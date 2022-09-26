package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cph
 * @since 2022-09-07
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {

    CoursePublishVo getPublishCourseInfo(@Param("courseId")String id);
}
