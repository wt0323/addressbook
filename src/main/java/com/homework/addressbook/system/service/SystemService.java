package com.homework.addressbook.system.service;

import com.homework.addressbook.entity.*;
import com.homework.addressbook.system.mapper.SystemMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService {
    @Autowired
    SystemMapper systemMapper;

    public User login(User user) {
        User selectUser = systemMapper.getUserByUserName(user.getUserName());
        if (selectUser != null){
            /*先获取名字，在判断密码*/
            String password = selectUser.getPassword();
            //密码加密 在跟 匹配，不对，密码就为错误，返回失败信息
            String s = DigestUtils.md5Hex(user.getPassword());
            if (StringUtils.equals(password, s)){
                selectUser.setPassword("");
                return selectUser;
            }
        }
        return null;
    }
    /*
    *   1.分页 参数，    2，查询参数    3.用户信息
    * */
    public LayuiReturn getAdmin(int page, int limit, String search, User user) {
        int start = (page - 1) * limit;
        //第一页 即使 0  -  50 记录
        List<User> users = systemMapper.getAdmin(start,limit,search);
        //记录数
        int count = systemMapper.getCount(search);
        //返回所需集合  和 记录数
        return LayuiReturn.success(count, users);
    }
    /*用户对应的菜单。*/
    public List<Navigation> getNavigation(Integer lv) {
        //根据管理员等级，获取相应菜单
        List<Navigation> navigation = systemMapper.getNavigation(lv);
        return navigation;
    }

    public LayuiReturn getAllNavigation(int lv) {
        /*获取等级*/
        List<Navigation> navigation = systemMapper.getNavigation(lv);
        int count = systemMapper.getNavigationCount();
        return LayuiReturn.success(count, navigation);
    }

    public ResultReturn addNavigation(Navigation navigation) {
        systemMapper.addNavigation(navigation);
        return  ResultReturn.success("操作成功");
    }

    public ResultReturn editNavigation(Navigation navigation) {
        //如果是系统管理目录下，则使用权限设置为超管
        if(navigation.getNavParent()==1){
            navigation.setNavUse(0);
        }
        systemMapper.editNavigation(navigation);
        return  ResultReturn.success("操作成功");
    }

    public Student studentLogin(Student student) {
        Student student1 = systemMapper.getStudentName(student.getLoginName());
        if (student1 != null){
            /*先获取名字，在判断密码*/
            String password = student1.getPassword();
            String s = DigestUtils.md5Hex(student.getPassword());
            if (StringUtils.equals(password, s)){
                student1.setPassword("");
                return student1;
            }
        }
        return null;
    }
    //获取 审核学生  账号
    public LayuiReturn checkStudent(int page, int limit) {
        int start = (page - 1) * limit;
        //第一页 即使 0  -  50 记录
        List<Student> students = systemMapper.getCheckStudent(start,limit);
        //记录数
        int count = systemMapper.getCheckCount();
        return LayuiReturn.success(count, students);
    }
    //根据id  更新审核状态
    public ResultReturn upStudentStatus(int id) {
        systemMapper.upStudentStatus(id);
        return ResultReturn.success();
    }
    //根据拒绝，更新 遣返理由
    public ResultReturn upStudentType(int id, int type) {
        systemMapper.upStudentType(id,type);
        return  ResultReturn.success();
    }

    //获取所有专业
    public LayuiReturn getAllMajor(int page, int limit, String search) {
        int start = (page - 1) * limit;
        //第一页 即使 0  -  50 记录
        List<Major> majors = systemMapper.getAllMajor(start,limit,search);
        int count = systemMapper.getAllMajorCount(search);
        return LayuiReturn.success(count, majors);
    }
    //获取所有学生
    public LayuiReturn getAllStudent(int page, int limit, String search) {
        int start = (page - 1) * limit;
        //第一页 即使 0  -  50 记录
        List<Student> students = systemMapper.getAllStudent(start,limit,search);
        int count = systemMapper.getAllStudentCount(search);
        //记录数，漫画list
        System.out.println(students);
        return LayuiReturn.success(count, students);
    }
    //根据id 禁用 账号
    public ResultReturn proStudent(int id) {
        systemMapper.proStudent(id);
        return ResultReturn.success();
    }
    //根据id  删除专业
    public ResultReturn delMajor(int id) {
        systemMapper.delMajor(id);
        return ResultReturn.success();
    }
    //根据 设置，新加专业
    public ResultReturn addMajor(String name) {
        //查看是否已经存在
        int num = systemMapper.getmajorName(name);
        if(num!=0){
            return ResultReturn.fail("已存在的专业");
        }
        systemMapper.addMajor(name);
        return ResultReturn.success();
    }

    public void UpStudentTime(int id) {
         systemMapper.UpStudentTime(id);
    }
}
