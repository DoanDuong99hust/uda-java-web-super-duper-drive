package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from Credentials")
    List<Credentials> findAll();

    @Select("select * from Credentials where credentialId = #{credentialId}")
    Credentials findById(Integer credentialId);

    @Insert("insert into Credentials(url, username, key, password, userId) " +
            "values (#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void create(Credentials credentials);

    @Update("update Credentials set url = #{url}, username = #{username}, key = #{key}, password = #{password} " +
            "where credentialId = #{credentialId}")
    void update(Credentials credentials);

    @Delete("delete from Credentials where credentialId = #{credentialId}")
    void delete(Integer credentialId);
}
