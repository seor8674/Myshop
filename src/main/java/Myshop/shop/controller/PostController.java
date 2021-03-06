package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.Post;
import Myshop.shop.entity.User;
import Myshop.shop.repository.PostRepository;
import Myshop.shop.repository.UserRepository;
import Myshop.shop.service.CommentService;
import Myshop.shop.service.PostService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final CommentService commentService;

    @GetMapping("/board")
    public String page(@AuthenticationPrincipal PrincipalDetails userDetails,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
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
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
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
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
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
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        Post post = postService.detail(id);
        post.addHit();
        model.addAttribute("title",post.getTitle());
        model.addAttribute("content",post.getContent());
        model.addAttribute("id",id);
        Post post1 = postRepository.findById(id).get();
        model.addAttribute("comment",post1.getCommentList());

        return "details";

    }
    @PostMapping("/comment")
    public String comment(@AuthenticationPrincipal PrincipalDetails userDetails,Long id,String content){
        String name = userDetails.getUser().getName();
        commentService.regist(id,name,content);

        return "redirect:/post/details?id="+id;
    }
    @GetMapping("/search")
    public String search(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
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
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
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
    @GetMapping("/mypost")
    public String mypage(@AuthenticationPrincipal PrincipalDetails userDetails,Model model) {
        try {
            model.addAttribute("name", userDetails.getUser().getName());
            model.addAttribute("check", true);
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
        } catch (NullPointerException e) {
            model.addAttribute("check", false);
        }
        String username = userDetails.getUsername();
        User byUserid = userRepository.findByUserid(username);
        List<Post> postList = byUserid.getPostList();
        model.addAttribute("post",postList);

        return "mypost";
    }
    @GetMapping("/mypost/update")
    public String mypageupdate(@AuthenticationPrincipal PrincipalDetails userDetails,Long id,Model model) {
        try {
            model.addAttribute("name", userDetails.getUser().getName());
            model.addAttribute("check", true);
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);
            }
        } catch (NullPointerException e) {
            model.addAttribute("check", false);
        }
        model.addAttribute("id",id);

        return "mypostupdate";
    }
    @PostMapping("/mypost/update")
    public String myupdate(Long id,String title,String content,Model model){
        postService.update(id,title,content);

        return "redirect:/mypost";
    }
    @GetMapping("/mypost/delete")
    public String mypagedelete(@AuthenticationPrincipal PrincipalDetails userDetails,Long id,Model model) {
        postService.delete(id);
        return "redirect:/mypost";
    }


}
