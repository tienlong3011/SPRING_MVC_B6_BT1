package com.codegym.blog.repository;

import com.codegym.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,Long> {
    @Query("FROM Blog b WHERE b.title like :title")
    Optional<Blog> findByTitle(@Param("title") String title);

    Optional<Blog> findBlogByTitleContaining(String title);
}
