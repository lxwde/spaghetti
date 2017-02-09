package com.lxwde.spaghetti.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
@Mapper
public interface PostMapper {
    @Select("select * from post")
    List<Post> getAll();

    @Select("select * from post where id=#{id}")
    Post getOneById(Integer id);

    @Insert("insert into post(title, content, blog_id, created_on) " +
            " values(#{title}, #{content}, #{blogId}, #{createdOn}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Update("update post set title=#{title}, content=#{content}, blog_id=#{blogId}, created_on=#{createdOn} " +
            " where id=#{id} ")
    void update(Post post);

    @Delete("delete from post where id=#{id}")
    void delete(Integer id);

}
