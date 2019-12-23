package com.homework.addressbook.system.controller;

import com.homework.addressbook.entity.*;
import com.homework.addressbook.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SystemController {
    @Autowired
    SystemService systemService;

    @ResponseBody
    @RequestMapping("/backstage")
    public ResultReturn backstage(User user, HttpSession session){
        System.out.println("登录账号"+user);
        if (user != null) {
            User user1 = systemService.login(user);
            if (user1 != null) {
                //获取用户权限以及用户菜单
                List<Navigation> navigation = systemService.getNavigation(user1.getLv());
                user1.setPassword("");
                System.out.println("用户等级："+user1.getLv());
                /*为了不每次刷新都进行数据库操作，将用户以及菜单内容保存于session里提高页面刷新加载效率*/
                session.setAttribute("navigation",navigation);
                System.out.println(navigation);
                session.removeAttribute("user");
                session.setAttribute("user", user1);
                return ResultReturn.success();
            }
        }
        return ResultReturn.fail("用户名或密码错误");
    }

    //获取管理员信息
    @ResponseBody
    @RequestMapping("/admin")
    public LayuiReturn comicPage(@RequestParam("page")int page, @RequestParam("limit")int limit, @RequestParam("search")String search, HttpSession session){
        User user = (User) session.getAttribute("user");
        return systemService.getAdmin(page,limit,search,user);
    }
    //获取需要审核学生
    @ResponseBody
    @RequestMapping("/checkStudent")
    public LayuiReturn checkStudent(@RequestParam("page")int page, @RequestParam("limit")int limit){
        return systemService.checkStudent(page,limit);
    }
    //审核通过，更改状态
    @ResponseBody
    @RequestMapping("/upStudentStatus")
    public ResultReturn upStudentStatus(@RequestParam("id")int id){
        return systemService.upStudentStatus(id);
    }
    //遣返，更新，遣返理由
    @ResponseBody
    @RequestMapping("/upStudentType")
    public ResultReturn upStudentType(@RequestParam("id")int id,@RequestParam("type") int type){
        return systemService.upStudentType(id,type);
    }
    //获取菜单
    @ResponseBody
    @RequestMapping("/getNavigation")
    public LayuiReturn getNavigation(@RequestParam("lv")int lv){
        return systemService.getAllNavigation(lv);
    }
    @ResponseBody
    @RequestMapping("/addNavigation")
    public ResultReturn addNavigation(Navigation navigation){
        return systemService.addNavigation(navigation);
    }
    //编辑菜单
    @ResponseBody
    @RequestMapping("/editNavigation")
    public ResultReturn editNavigation(Navigation navigation){
        return systemService.editNavigation(navigation);
    }
    /*获取全部正常学生信息*/
    @ResponseBody
    @RequestMapping("/getAllStudent")
    public LayuiReturn getAllStudent(@RequestParam("page")int page, @RequestParam("limit")int limit, @RequestParam("search")String search){
        return systemService.getAllStudent(page,limit,search);
    }
    /*获取全部正常学生信息*/
    @ResponseBody
    @RequestMapping("/getAllMajor")
    public LayuiReturn getAllMajor(@RequestParam("page")int page, @RequestParam("limit")int limit, @RequestParam("search")String search){
        return systemService.getAllMajor(page,limit,search);
    }

    /*禁用学生账号*/
    @ResponseBody
    @RequestMapping("/proStudent")
    public ResultReturn proStudent(@RequestParam("id")int id){
        return systemService.proStudent(id);
    }
    /*删除专业*/
    @ResponseBody
    @RequestMapping("/delMajor")
    public ResultReturn delMajor(@RequestParam("id")int id){
        return systemService.delMajor(id);
    }
    /*添加专业*/
    @ResponseBody
    @RequestMapping("/addMajor")
    public ResultReturn xxxMajor(@RequestParam("name")String name){
        return systemService.addMajor(name);
    }
}
