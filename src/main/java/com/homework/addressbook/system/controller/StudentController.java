package com.homework.addressbook.system.controller;

import com.homework.addressbook.entity.ResultReturn;
import com.homework.addressbook.entity.Student;
import com.homework.addressbook.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;


    /*学生端*/

    /*登录验证后，进入后台*/
    @ResponseBody
    @RequestMapping("/student/backstage")
    public ResultReturn studentBackstage(Student student, HttpSession session){
        System.out.println("登录账号"+student.getLoginName());
        return studentService.studentLogin(student,session);
    }
    /*学生注册*/
    @ResponseBody
    @RequestMapping("/student/register")
    public ResultReturn studentRegister(Student student){
        System.out.println("注册信息"+student);
        return studentService.checkRegister(student);
    }

    /*遣返，重新注册，更新信息*/
    @ResponseBody
    @RequestMapping("/student/upInfo")
    public ResultReturn upInfo(Student student,HttpSession session){
        System.out.println("完善信息信息"+student);
        return studentService.upStudentInfo(student,session);
    }
}
