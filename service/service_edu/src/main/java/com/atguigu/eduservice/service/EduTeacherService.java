package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ck
 * @since 2021-06-30
 */
public interface EduTeacherService extends IService<EduTeacher> {

    R getTeacher(String id);

}
