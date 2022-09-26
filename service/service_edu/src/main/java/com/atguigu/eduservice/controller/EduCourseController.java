package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.Config.vo.CourseQuery;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cph
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){


        String id = courseService.saveCourseInfo(courseInfoVo);
        return  R.ok().data("courseId",id);
    }


    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo( @PathVariable String courseId){

        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }


    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }




    //课程发布
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id){

        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        boolean b = courseService.updateById(course);
        if (b){
            return  R.ok();
        }else{
            return  R.error();

        }
    }


    @PostMapping("/pageCoursecondition/{current}/{limit}")
    public R pageCoursecondition (@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required=false) CourseQuery course){


        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        System.out.println("========"+course.getTitle());
        String title =course.getTitle();
        String status = course.getStatus();

        if (!StringUtils.isEmpty(title) ){

            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status) &&  !"1".equals(status)  ){

            wrapper.eq("status",status);
        }

        courseService.page(page,wrapper);
        System.out.println("-----------------------------");
        System.out.println(
                page.getTotal()
        );


        System.out.println(
                page.getRecords()
        );
        return  R.ok().data("total",page.getTotal()).data("items",page.getRecords());
    }


}

