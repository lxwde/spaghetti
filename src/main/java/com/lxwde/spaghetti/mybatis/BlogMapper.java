package com.lxwde.spaghetti.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
@Mapper
public interface BlogMapper {
    @Select("select * from blog")
    List<Blog> getAll();

    @ResultMap("BlogResult")
    @Select("select * from blog left outer join post on blog.id=post.blog_id where blog.id=#{id}")
    Blog getOneById(Integer id);

    @Insert("insert into blog(name, created_on) values(#{name}, #{createdOn}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Blog blog);

    @Update("update blog set name=#{name}, created_on=#{createdOn} " +
            " where id={id} ")
    void update(Blog blog);

    @Delete("delete from blog where id=#{id}")
    void delete(Integer id);
}
