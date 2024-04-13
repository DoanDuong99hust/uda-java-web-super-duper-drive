package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from Files where userId = #{userId}")
    List<Files> findAllByUserId(Integer userId);

    @Select("select * from Files where fileId = #{fileId}")
    Files findById(Integer fileId);

    @Select("select * from Files where fileName = #{name}")
    Files findByName(String name);

    @Insert("insert into Files(fileName, contentType, fileSize, fileData, userId) " +
            "values (#{fileName},#{contentType},#{fileSize},#{fileData},#{userId})")
    Integer insert(Files files);

    @Update("update Files set fileName = #{fileName}, contentType = #{contentType}, fileSize = #{fileSize}, fileData = #{fileData} " +
            "where fileId = #{fileId}")
    Integer update(Files files);

    @Delete("delete from Files where fileId = #{fileId}")
    void delete(Integer fileId);
}
