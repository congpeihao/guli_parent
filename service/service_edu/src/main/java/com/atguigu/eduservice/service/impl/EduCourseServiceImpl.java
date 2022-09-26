package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cph
 * @since 2022-09-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduVideoMapper videoMapper;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);

        if (insert<=0){
            throw new GuliException(20001,"插入数据失败");
        }

        //获取添加之后课程id
        String cid = course.getId();

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        descriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        EduCourse course = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(course,courseInfoVo);

        EduCourseDescription courseDescription = descriptionService.getById(courseId);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);

        int i = baseMapper.updateById(course);

        if (i<=0){
            throw new GuliException(20001,"修改数据失败");
        }

        EduCourseDescription courseDescription = new EduCourseDescription();

        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());

        boolean b = descriptionService.updateById(courseDescription);


    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {


        return videoMapper.getPublishCourseInfo(id);
    }
}
