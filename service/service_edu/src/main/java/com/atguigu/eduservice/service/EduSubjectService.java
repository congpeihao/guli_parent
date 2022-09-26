package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cph
 * @since 2022-09-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile multipartFile, EduSubjectService subjectService);


    List<OneSubject> getAllsubJect();
}
