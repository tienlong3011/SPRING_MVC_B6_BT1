package com.codegym.blog.controller.restController;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<Iterable<Category>>findAllBlog(){
        List<Category>categories = (List<Category>) categoryService.findAll();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category>findBlogById(@PathVariable Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(!categoryOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categoryOptional.get(),HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Category>saveBlog(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category>updateBlog(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(!categoryOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category.setId(categoryOptional.get().getId());
        categoryService.save(category);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCustomer(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(id);
        return new ResponseEntity<>(categoryOptional.get(), HttpStatus.NO_CONTENT);
    }
}