package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.Config.vo.TeacherQuery;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cph
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/findall")
    public R findall(){

        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }


    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b){
           return R.ok();
        }else{
           return R.error();
        }
    }

    @GetMapping("pageTeacher/{current}/{pagesize}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long pagesize){


        //创建page对象

        Page<EduTeacher> pageTeacher = new Page<>(current,pagesize);


       /* try {
            int a =10/0;
        } catch (Exception e) {
            throw new GuliException(20001,"自定义异常错误......");
        }
*/

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

        return R.ok().data("total",total).data("rows",records);

    }

    @PostMapping("pageTeachercondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required=false)TeacherQuery teacherQuery){

        Page<EduTeacher> teacherPage = new Page<>(current,limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();


        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("gmt_create");

        teacherService.page(teacherPage,wrapper);

        return  R.ok().data("total",teacherPage.getTotal()).data("items",teacherPage.getRecords());
    }



    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){

        boolean save = teacherService.save(teacher);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }

    }


    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable Long id){

        EduTeacher byId = teacherService.getById(id);

        return R.ok().data("item",byId);

    }

    @PostMapping("updateTeacher")
    public  R updateTeacher (@RequestBody EduTeacher teacher){
        boolean b = teacherService.updateById(teacher);
        if (b){
            return  R.ok();
        }else{
            return R.error();
        }
    }

}

