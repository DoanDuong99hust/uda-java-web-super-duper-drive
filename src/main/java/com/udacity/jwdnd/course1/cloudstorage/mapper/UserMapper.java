package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from Users where userId = #{userId}")
    Users findById(Integer userId);

    @Select("select * from Users where username = #{username}")
    Users findByUsername(String username);

    @Insert("insert into Users(username, salt, password, firstname, lastname) " +
            "values(#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(Users users);

    @Update("update Users set username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstname}, lastname = #{lastname} " +
            "where userId = #{userId}")
    Integer update(Users users);

    @Delete("delete from Users where userId = #{userId}")
    void deleteById(Integer userId);
}
