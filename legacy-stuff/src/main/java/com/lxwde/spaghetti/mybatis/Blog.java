package com.lxwde.spaghetti.mybatis;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blog {

    private Integer id;
    private String name;
    private Date createdOn;
    private List<Post> posts = new ArrayList<Post>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Blog{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", createdOn=").append(createdOn);
        sb.append(", posts=").append(posts);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blog)) return false;

        Blog blog = (Blog) o;

        if (id != null ? !id.equals(blog.id) : blog.id != null) return false;
        if (name != null ? !name.equals(blog.name) : blog.name != null) return false;
        if (createdOn != null ? !createdOn.equals(blog.createdOn) : blog.createdOn != null) return false;
        return posts != null ? posts.equals(blog.posts) : blog.posts == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (posts != null ? posts.hashCode() : 0);
        return result;
    }
}