package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cph
 * @since 2022-09-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<OneSubject> getAllsubJect() {

        QueryWrapper<EduSubject> onewrapper = new QueryWrapper<>();
        onewrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(onewrapper);

        QueryWrapper<EduSubject> twowrapper = new QueryWrapper<>();
        twowrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twowrapper);

        List<OneSubject> onelist=new ArrayList<>();


        for (EduSubject oneSubject : oneSubjects) {

            OneSubject one = new OneSubject();
            BeanUtils.copyProperties(oneSubject,one);


            List<TwoSubject> twolist=new ArrayList<>();
            for (EduSubject twoSubject : twoSubjects) {

                TwoSubject two = new TwoSubject();

                if (twoSubject.getParentId().equals(one.getId())){

                    BeanUtils.copyProperties(twoSubject,two);
                    twolist.add(two);
                }
            }
            one.setChildren(twolist);
            onelist.add(one);
        }

        return onelist;

    }
}
