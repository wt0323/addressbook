package com.homework.addressbook.system.mapper;


import com.homework.addressbook.entity.Major;
import com.homework.addressbook.entity.Student;
import com.homework.addressbook.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentMapper extends Mapper<User> {

    Student getStudentName(@Param("loginName") String loginName);

    void updateStudent(Student student);

    void addStudent(Student student);

    List<Student> getAllStudentInfo(@Param("search") String search);

    List<Major> getStudentNavigation();

    List<Student> getStudentInfoByMajorId(@Param("majorId")int majorId,@Param("search") String search);

    void upStudentInfo(Student student);


    Student getStudentInfo(@Param("id")Integer id);


    void UpStudentTime(Integer id);

    void delStudentById(@Param("id")Integer id);
}
