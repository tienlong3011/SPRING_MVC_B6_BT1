package com.codegym.blog.controller;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.BlogForm;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.blog.IBlogService;
import com.codegym.blog.service.category.ICategoryService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("categories")
    public Iterable<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("")
    public ModelAndView showList(){
        Iterable<Blog>blogs = blogService.findAll();
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("blogForm", new BlogForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView CreateBlog(@ModelAttribute BlogForm blogForm){
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(blogForm.getImage().getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Blog blog = new Blog(blogForm.getTitle(), blogForm.getContent(), blogForm.getAuthor(), fileName, blogForm.getCategory());
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("blogForm", new BlogForm());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) throws IOException {
        Optional<Blog> blog = blogService.findById(id);
        ModelAndView modelAndView;
        if(blog.isPresent()){
            File file = new File(fileUpload + blog.get().getImage());
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("fileNew",file.getName(),"png,.../img", IOUtils.toByteArray(inputStream));
            BlogForm blogForm = new BlogForm(id,blog.get().getTitle(),blog.get().getContent(),blog.get().getAuthor(),multipartFile,blog.get().getCategory());
            modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("blogForm", blogForm);
        }else {
            modelAndView = new ModelAndView("error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView update(@ModelAttribute("blogForm") BlogForm blogForm)  {
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(blogForm.getImage().getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Blog blog = new Blog(blogForm.getId(),blogForm.getTitle(), blogForm.getContent(), blogForm.getAuthor(), fileName, blogForm.getCategory());
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("blogForm", blogForm);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("blog",blog);
        return modelAndView;

    }

    @PostMapping("/delete")
    public ModelAndView deleteCustomer(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return new ModelAndView("redirect:/blog");
    }

    @GetMapping("/view/{id}")
    public ModelAndView showViewForm(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/view");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }
}
