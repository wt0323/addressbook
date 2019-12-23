package com.homework.addressbook.system.service;

import com.homework.addressbook.entity.Major;
import com.homework.addressbook.entity.ResultReturn;
import com.homework.addressbook.entity.Student;
import com.homework.addressbook.system.mapper.StudentMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public ResultReturn studentLogin(Student student, HttpSession session) {
        Student student1 = studentMapper.getStudentName(student.getLoginName());
        if (student1 != null){
            /*如果账号存在，判断状态*/
            if(student1.getStatus()==1){
                if(student1.getType()==1){
                    /*遣返后，提示用户错误信息，然后删掉改账号*/
                    studentMapper.delStudentById(student1.getId());
                    return ResultReturn.fail("登录失败，你的账号未能通过审核，已被删除，请重新注册，错误原因：班级有误。");
                }
                if(student1.getType()==2){
                    studentMapper.delStudentById(student1.getId());
                    return ResultReturn.fail("登录失败，你的账号未能通过审核，已被删除，请重新注册，错误原因：专业有误。");
                }
                if(student1.getType()==3){
                    studentMapper.delStudentById(student1.getId());
                    return ResultReturn.fail("登录失败，你的账号未能通过审核，已被删除，请重新注册，错误原因：姓名有误。");
                }
                return ResultReturn.fail("登录失败，你的账号还在审核中ing。。");
            }
            if(student1.getStatus()==2){
                return ResultReturn.fail("登录失败，你的账号已被禁用，请联系管理员解决。");
            }
            /*先审核账号，在审核密码*/
            String password = student1.getPassword();
            String s = DigestUtils.md5Hex(student.getPassword());
            if (StringUtils.equals(password, s)){
                //更新登录次数和时间
                studentMapper.UpStudentTime(student1.getId());
                student1.setPassword("");
                session.removeAttribute("user");
                //获取菜单
                List<Major> majors =  studentMapper.getStudentNavigation();
                session.setAttribute("user", student1);
                session.setAttribute("majors", majors);
                System.out.println(session.getAttribute("user"));
                return ResultReturn.success();
            }
        }
        return ResultReturn.fail("账号密码有误");
    }

    public ResultReturn checkRegister(Student student) {
        Student student1 = studentMapper.getStudentName(student.getLoginName());
        System.out.println("获取："+student1);
        //存在则审核
        if(student1 != null){
            //存在则判断，审核状态
            if (student1.getStatus()==0){
                return ResultReturn.fail("注册失败，该账号已存在，请前往登录。");
            }else if (student1.getStatus()==2){
                return ResultReturn.fail("注册失败，该账号已经被禁用，请重新注册。");
            }else {
                //执行注册，审核中的不可以覆盖，审核不通过的账号允许覆盖重置。
                if(student1.getType()!=0){
                    student.setPassword(DigestUtils.md5Hex(student.getPassword()));
                    studentMapper.updateStudent(student);
                    return ResultReturn.success("已经更正资料，等待管理员重新审核");
                }else {
                    return ResultReturn.fail("注册失败，该账号还在审核中，请等待管理员审核或寻找管理员解决。");
                }
            }
        }else {
            //不存在则新增，
            student.setPassword(DigestUtils.md5Hex(student.getPassword()));
            studentMapper.addStudent(student);
            return  ResultReturn.success("注册成功，请等待管理员审核。");
        }
    }

    public List<Student> getAllStudentInfo(String search) {
        return studentMapper.getAllStudentInfo(search);
    }

    public List<Student> getStudentInfoByMajorId(int majorId,String search) {
        return studentMapper.getStudentInfoByMajorId(majorId,search);
    }

    public ResultReturn upStudentInfo(Student student,HttpSession session) {
        studentMapper.upStudentInfo(student);
        Student student1 = studentMapper.getStudentInfo(student.getId());
        if(student1!=null){
            student1.setPassword("");
            session.removeAttribute("user");
            //更新session
            session.setAttribute("user", student1);
            return ResultReturn.success();
        }
        return ResultReturn.fail();
    }

    public List<Major> getStudentNavigation() {
        return studentMapper.getStudentNavigation();
    }
}
