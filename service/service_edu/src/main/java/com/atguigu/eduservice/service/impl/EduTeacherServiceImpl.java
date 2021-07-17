package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author ck
 * @since 2021-06-30
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Resource
    private EduTeacherMapper eduTeacherMapper;

    @Cacheable(cacheNames = "teacher",key = "#root.args[0]")
    @Override
    public R getTeacher(String id) {
        EduTeacher eduTeacher = eduTeacherMapper.selectById(id);
        return R.ok().data("eduTeacher", eduTeacher);
    }
}
