package com.codegym.blog.controller;

import com.codegym.blog.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class LoginController {

    @ModelAttribute("user")
    public User setUpUserForm(){
        return new User();
    }

    @RequestMapping("/login")
    public ModelAndView Index(@CookieValue(value = "setUser",defaultValue = "") String setUser){
        Cookie cookie = new Cookie("setUser",setUser);
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("cookieValue", cookie);
        return modelAndView;
    }

    @PostMapping("/dologin")
    public String doLogin(@ModelAttribute("user") User user, Model model, @CookieValue(value = "setUser",defaultValue = "")
            String setUser, HttpServletRequest request, HttpServletResponse response){
        //implement business logic
        if(user.getEmail().equals("admin@gmail.com")&& user.getPassword().equals("12345")){
            if(user.getEmail() != null)
                setUser = user.getEmail();
            // create cookie and set it in response
            Cookie cookie = new Cookie("setUser",setUser);
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            //get all cookies
            Cookie[]cookies = request.getCookies();
            //iterate each cookie
            for (Cookie ck : cookies){
                //display only the cookie with the name 'setUser'
                if(ck.getName().equals("setUser")){
                    model.addAttribute("cookieValue",ck);
                    break;
                } else {
                    ck.setValue("");
                    model.addAttribute("cookieValue",ck);
                    break;
                }
            }
            model.addAttribute("message", "Login success. Welcome ");
            return "redirect:/blog";
        } else {
            user.setEmail("");
            Cookie cookie = new Cookie("setUser", setUser);
            model.addAttribute("cookieValue", cookie);
            model.addAttribute("message", "Login failed. Try again.");
            return "/login";
        }

    }

}
