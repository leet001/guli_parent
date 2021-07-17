package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.annotation.ParameterLog;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ck
 * @since 2021-06-30
 */
@RestController
@RequestMapping("/eduservice/teacher")
@Api(tags = "讲师管理Controller")
@CrossOrigin
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    // 查询讲师数据
    // rest风格
    @GetMapping("/findAll")
    @ApiOperation("查询所有讲师")
    public R findAllTeacher() {
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("items", teachers);
    }

    // 逻辑删除讲师
    @DeleteMapping("/delete/{id}")
    @ApiOperation("逻辑删除讲师")
    public R removeTeacher(@PathVariable("id") String id) {

        boolean b = eduTeacherService.removeById(id);
        return b ? R.ok() : R.error();
    }

    // 分页查询讲师
    @GetMapping("pageTeacher/{current}/{size}")
    @ApiOperation("分页查询讲师")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("size") long size) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常");
        }

        Page<EduTeacher> page = new Page<>(current, size);
        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    // 条件分页查询讲师
    @PostMapping("pageTeacherCondition/{current}/{size}")
    @ApiOperation("条件分页查询讲师")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("size") long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageTeacher = new Page<>(current, size);

        // 条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_create", teacherQuery.getEnd());
        }

        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    // 添加讲师接口
    @PostMapping("/addTeacher")
    @ApiOperation("添加讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.save(eduTeacher);
        return b ? R.ok() : R.error();
    }

    // 根据讲师id查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation("根据讲师id查询")
    @ParameterLog
    public R getTeacher(@PathVariable("id") String id) {
        return eduTeacherService.getTeacher(id);
    }
    // 讲师修改功能
    @PutMapping("/updateTeacher")
    @ApiOperation("讲师修改功能")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        System.out.println(eduTeacher);
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }


}

