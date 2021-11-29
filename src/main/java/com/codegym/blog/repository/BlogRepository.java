package com.codegym.blog.repository;

import com.codegym.blog.model.Blog;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BlogRepository implements IBlogRepository{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Blog> findAll() {
        TypedQuery<Blog>query = entityManager.createQuery("select b from Blog b",Blog.class);
        return query.getResultList();
    }

    @Override
    public Blog findById(Long id) {
        TypedQuery<Blog>query = entityManager.createQuery("select b from Blog b where b.id = :id",Blog.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(Blog blog) {
        if(blog.getId() != null){
            entityManager.merge(blog);
        } else {
            entityManager.persist(blog);
        }
    }

    @Override
    public void remove(Long id) {
        Blog blog = findById(id);
        if(blog != null){
            entityManager.remove(blog);
        }
    }
}
