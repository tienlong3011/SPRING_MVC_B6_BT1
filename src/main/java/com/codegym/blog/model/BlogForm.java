package com.codegym.blog.model;

import org.springframework.web.multipart.MultipartFile;

public class BlogForm {
    private Long id;
    private String title;
    private String content;
    private String author;
    private MultipartFile image;
    private Category category;

    public BlogForm() {
    }

    public BlogForm(String title, String content, String author, MultipartFile image,Category category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.image = image;
        this.category = category;
    }


    public BlogForm(Long id, String title, String content, String author, MultipartFile image, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
