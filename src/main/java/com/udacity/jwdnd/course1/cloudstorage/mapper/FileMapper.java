package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    @Select("select * from Files where fileId = #{fileId}")
    Files findById(Integer fileId);

    @Insert("insert into Files(filename, contentType, fileSize, fileData, userId) " +
            "values (#{filename},#{contentType},#{fileSize},#{fileData},#{userId})")
    Integer insert(Files files, Integer userId);

    @Update("update Files set fileName = #{fileName}, contentType = #{contentType}, fileSize = #{fileSize}, fileData = #{fileData} " +
            "where fileId = #{fileId}")
    Integer update(Files files);

    @Delete("delete from Files where fileId = #{fileId}")
    void delete(Integer fileId);
}
