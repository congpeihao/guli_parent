package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cph
 * @since 2022-09-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据ID查询章节集合
        QueryWrapper<EduChapter> wrapperchapter= new QueryWrapper<>();
        wrapperchapter.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperchapter);

        //根据ID查询小节集合
        QueryWrapper<EduVideo> wrapperVideo= new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> VideoList = videoService.list(wrapperVideo);


        List<ChapterVo> listChapterVo= new ArrayList<>();
        for (EduChapter eduChapter : chapterList) {

            List<VideoVo> listVideoVo = new ArrayList<>();

            ChapterVo chapterVo = new ChapterVo();

            BeanUtils.copyProperties(eduChapter,chapterVo);

            for (EduVideo eduVideo : VideoList) {

                if (eduVideo.getChapterId().equals(eduChapter.getId())){

                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    listVideoVo.add(videoVo);
                }
            }
            chapterVo.setChildren(listVideoVo);
            listChapterVo.add(chapterVo);
        }
        return listChapterVo;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count>0){

            throw new GuliException(20001,"章节下有小节，删除失败");
        }

        int i = baseMapper.deleteById(chapterId);
        return i>0;
    }
}
