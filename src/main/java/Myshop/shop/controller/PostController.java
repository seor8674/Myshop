package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.Post;
import Myshop.shop.entity.User;
import Myshop.shop.repository.PostRepository;
import Myshop.shop.repository.UserRepository;
import Myshop.shop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Scanner;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/board")
    public String page(@AuthenticationPrincipal PrincipalDetails userDetails,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e) {
            model.addAttribute("check", false);
        }

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> all = postRepository.findAll(pageRequest);
        List<Post> content = all.getContent();
        model.addAttribute("post",content);
        model.addAttribute("page",1);
        if(all.getTotalPages()==0){
            model.addAttribute("count",1);
        }
        else{
            model.addAttribute("count",all.getTotalPages());
        }

        return "board";

    }
    @GetMapping("/board/page")
    public String pages(@AuthenticationPrincipal PrincipalDetails userDetails,int page,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e) {
            model.addAttribute("check", false);
        }

        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Post> all = postRepository.findAll(pageRequest);
        List<Post> content = all.getContent();
        model.addAttribute("post",content);
        model.addAttribute("page",page);
        if(all.getTotalPages()==0){
            model.addAttribute("count",1);
        }
        else{
            model.addAttribute("count",all.getTotalPages());
        }

        return "board";

    }

    @GetMapping("/register")
    public String reg(@AuthenticationPrincipal PrincipalDetails userDetails,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        return "register";
    }


    @PostMapping("/register")
    public String register(@AuthenticationPrincipal PrincipalDetails principalDetails, Post post){
        String userid = principalDetails.getUsername();
        User byUserid = userRepository.findByUserid(userid);
        post.setUser(byUserid);
        post.setHit(0);
        postRepository.save(post);

        return "redirect:/board";
    }

    @GetMapping("/post/details")
    public String detail(@AuthenticationPrincipal PrincipalDetails userDetails,@RequestParam Long id, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        Post post = postService.detail(id);
        post.addHit();
        model.addAttribute("title",post.getTitle());
        model.addAttribute("content",post.getContent());

        return "details";

    }
    @GetMapping("/search")
    public String search(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> byTitleContaining = postRepository.findByTitleContaining(search, pageRequest);
        List<Post> content = byTitleContaining.getContent();
        model.addAttribute("search",search);
        model.addAttribute("post",content);
        model.addAttribute("page",1);
        model.addAttribute("count",byTitleContaining.getTotalPages());

        return "search";
    }
    @GetMapping("/search/page")
    public String searchpage(@AuthenticationPrincipal PrincipalDetails userDetails,int page,Model model,String search){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Post> all = postRepository.findByTitleContaining(search,pageRequest);
        List<Post> content = all.getContent();
        model.addAttribute("search",search);
        model.addAttribute("page",page);
        model.addAttribute("post",content);
        model.addAttribute(
                "count",all.getTotalPages());

        return "search";

    }



}
