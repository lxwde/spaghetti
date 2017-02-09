package com.lxwde.spaghetti.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAll();

    @ResultMap(value = "UserResult")
    @Select("select * from user left outer join blog on user.blog_id=blog.id where user.id=#{id}")
    User getOneById(Integer id);

    @Insert("insert into user (email, password, first_name, last_name, blog_id) " +
            " values(#{email}, #{password}, #{firstName}, #{lastName}, #{blog.id}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("update user set email=#{email}, password=#{password}, " +
            " first_name=#{firstName}, last_name=#{lastName}, blog_id=#{blog.id} " +
            " where id=#{id}")
    void update(User user);

    @Delete("delete from user where id=#{id}")
    void delete(Integer id);
}
