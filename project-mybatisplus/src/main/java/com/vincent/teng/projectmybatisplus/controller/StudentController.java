package com.vincent.teng.projectmybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vincent.teng.projectmybatisplus.entity.Student;
import com.vincent.teng.projectmybatisplus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengxiao
 * @since 2019-05-29
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/doUpdateTest")
    public void updateTest(){
        Student student= new Student();
        student.setId(1);
        student.setName("kkkkkkkkkkkkk");
        studentService.updateById(student);
    }

    @PostMapping(value = "/doUpdateTest2")
    public void updateTest2(){
        Student student= new Student();
        student.setAddress("河南省鹿邑县2");

        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("id","1");
        studentService.update(student,updateWrapper);
    }
}

