package com.codegym.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //fetch = FetchType.EAGER ( tìm kiếm kèm theo được thằng category)
    // cascade = CascadeType.PERSIST ( xóa thằng cha thì thằng con tự null trường đấy )
    //mappedBy = "category" (không thêm bảng phụ)
//, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "category"
    @OneToMany(targetEntity = Blog.class)
    @JsonBackReference
    private List<Blog>blogs;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, List<Blog> blogs) {
        this.name = name;
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
