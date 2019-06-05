package com.vincent.teng.projectmybatisplus.service.impl;

import com.vincent.teng.projectmybatisplus.entity.Student;
import com.vincent.teng.projectmybatisplus.mapper.StudentMapper;
import com.vincent.teng.projectmybatisplus.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengxiao
 * @since 2019-05-29
 */
@Service
public class StudentImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
