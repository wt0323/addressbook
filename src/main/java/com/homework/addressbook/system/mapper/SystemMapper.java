package com.homework.addressbook.system.mapper;


import com.homework.addressbook.entity.Major;
import com.homework.addressbook.entity.Navigation;
import com.homework.addressbook.entity.Student;
import com.homework.addressbook.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SystemMapper extends Mapper<User> {
    User getUserByUserName(@Param("userName") String userName);

    List<User> getAdmin(int start, int limit, String search);
    int getCount(String search);
    //用户专属菜单
    List<Navigation> getNavigation(@Param("lv") Integer lv);

    int getNavigationCount();
    //添加菜单
    void addNavigation(Navigation navigation);

    void editNavigation(Navigation navigation);

    Student getStudentName(@Param("loginName") String loginName);

    List<Student> getCheckStudent(@Param("start")int start, @Param("limit")int limit);

    int getCheckCount();

    void upStudentStatus(@Param("id") int id);

    void upStudentType(@Param("id")int id, @Param("type")int type);

    List<Student> getAllStudent(@Param("start")int start, @Param("limit")int limit,@Param("search") String search);

    List<Major> getAllMajor(@Param("start")int start, @Param("limit")int limit, @Param("search") String search);

    int getAllStudentCount(String search);

    int getAllMajorCount(String search);

    void proStudent(@Param("id") int id);

    void delMajor(@Param("id") int id);

    void addMajor(@Param("name")String name);

    int getmajorName(String name);

    void UpStudentTime(@Param("id") int  id);
}
