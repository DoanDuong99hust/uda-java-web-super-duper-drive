package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    @Select("select * from Notes where noteId = #{noteId}")
    Notes findById(Integer noteId);

    @Insert("insert into Notes(noteTitle, noteDescription, userId) " +
            "values (#{noteTitle},#{noteDescription},#{userId})")
    Integer insert(Notes notes, Integer userId);

    @Update("update Notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            "where noteId = #{noteId}")
    Integer update(Notes notes);

    @Delete("delete from Notes where noteId = #{noteId}")
    void delete(Integer noteId);
}