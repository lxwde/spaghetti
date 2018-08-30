package com.lxwde.spaghetti.mybatis;

import com.lxwde.spaghetti.mybatis.Blog;
import com.lxwde.spaghetti.mybatis.BlogMapper;
import com.lxwde.spaghetti.mybatis.PostMapper;
import com.lxwde.spaghetti.mybatis.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogMapperTest {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void test(){
        Blog blog = new Blog();
        blog.setName("my first blog");
        blog.setCreatedOn(new Date());
        blogMapper.insert(blog);
        assertThat(blog.getId()).isGreaterThan(0);

        Post post = new Post();
        post.setTitle("my first post");
        post.setContent("my first content");
        post.setBlogId(blog.getId());
        post.setCreatedOn(new Date());
        postMapper.insert(post);
        assertThat(post.getId()).isGreaterThan(0);

        Post postRet = postMapper.getOneById(post.getId());
        System.out.println(postRet);

        Blog blogRet = blogMapper.getOneById(blog.getId());
        System.out.println(blogRet);

        User user = new User();
        user.setBlog(blog);
        user.setEmail("my_first_email@dummy.com");
        user.setFirstName("my_first_name");
        user.setLastName("my_last_name");
        user.setPassword("my_password");
        userMapper.insert(user);
        assertThat(user.getId()).isGreaterThan(0);

        User userRet = userMapper.getOneById(user.getId());
        System.out.println(userRet);
        assertThat(userRet).isEqualTo(user);
    }
}
