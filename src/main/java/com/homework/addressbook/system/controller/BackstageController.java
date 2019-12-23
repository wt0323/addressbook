package com.homework.addressbook.system.controller;


import com.homework.addressbook.entity.Major;
import com.homework.addressbook.entity.Student;
import com.homework.addressbook.entity.User;
import com.homework.addressbook.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/***
 * @author 有问题qq592421358
 */
@Controller
public class BackstageController {
    @Autowired
    StudentService studentService;

    /*学生端*/

    /*学生登录接口*/
    @RequestMapping("/")
    public String studentLogin(HttpSession session) {
        List<Major> majors = studentService.getStudentNavigation();
        session.setAttribute("major", majors);
        return "/student/login";
    }
    /*学生端退出，清除用户信息*/
    @RequestMapping("/student/loginOut")
    public String studentLoginOut(HttpSession session) {
        //获取专业信息
        session.removeAttribute("student");
        return "/student/login";
    }
    /*进入系统主页面*/
    @RequestMapping("/student/index")
    public String studentBackstage(HttpSession session) {
        //后台主页参数配置
        Student user = (Student) session.getAttribute("user");
        System.out.println("欢迎：" + user.getLoginName());
        return "/student/index";
    }


    //动态页面跳转接口，1.父文件夹 2.对应html文件
    @RequestMapping("/{nav}/{urlName}")
    public String url(@PathVariable(value = "urlName") String urlName, @PathVariable String nav) {
        return "/" + nav + "/" + urlName;
    }


    //动态页面跳转接口，学生卡动态 配置，    name:专业id   search 搜索关键词   默认no ，no不带搜索词
    @RequestMapping("/info/{name}/{search}")
    public String infoUrl(@PathVariable(value = "name") String name, Model model, @PathVariable String search) {
        //为搜索按钮传递参数， 具体用处 参照 info.html  js逻辑，
        //有问题qq592421358
        model.addAttribute("info", name);
        if(search.equals("no")){search="";}
        if (name.equals("all")) {   //展示全部学生卡
            //全部 学生信息
            List<Student> students = studentService.getAllStudentInfo(search);
            int i =1;
            //搜索没有  则显示提示信息。   size 1 有数据 不提示  0 提示
            if(students.size()==0){i=0;}
            model.addAttribute("size", i);
            model.addAttribute("students", students);
        } else {
            //根据专业分配显示对于名片
            List<Student> students = studentService.getStudentInfoByMajorId(Integer.parseInt(name),search);
            System.out.println();
            if (students.size() != 0) {
                model.addAttribute("size", 1);
            } else {
                model.addAttribute("size", 0);
            }
            model.addAttribute("students", students);
        }
        return "/student/info";
    }

    /*管理端*/

    /*登录接口*/
    @RequestMapping("/admins")
    public String adminLogin() {
        return "/backstage/login";
    }

    //退出接口
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("user");
        return "/backstage/login";
    }
    //主页接口
    @RequestMapping("/index")
    public String backstage(HttpSession session) {
        //后台主页参数配置
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "/backstage/login";
        }
        System.out.println("欢迎：" + user.getRemark());
        return "/backstage/index";
    }
}
