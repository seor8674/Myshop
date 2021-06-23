package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.Book;
import Myshop.shop.entity.Post;
import Myshop.shop.repository.BookRepository;
import Myshop.shop.repository.PostRepository;
import Myshop.shop.service.BookService;
import Myshop.shop.service.PostService;
import Myshop.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PostRepository postRepository;
    private final PostService postService;
    private final BookRepository bookRepository;
    private final BookService bookService;


    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal PrincipalDetails userDetails, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        return "admin";
    }

    @GetMapping("/admin/board")
    public String adminboard(@AuthenticationPrincipal PrincipalDetails userDetails, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
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
        return "adminboard";

    }
    @GetMapping("/admin/board/page")
    public String adminboardpage(@AuthenticationPrincipal PrincipalDetails userDetails,int page, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
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

        return "adminboard";

    }
    @GetMapping("admin/board/delete")
    public String adminboarddelete(Long id){
        postService.delete(id);
        return "redirect:/admin/board";
    }
    @GetMapping("/admin/search")
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

        return "adminsearch";
    }
    @GetMapping("/admin/search/page")
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

        return "adminsearch";

    }
    @GetMapping("/admin/booklist")
    public String itemlist(@AuthenticationPrincipal PrincipalDetails userDetails, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }


        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> all = bookRepository.findAll(pageRequest);
        List<Book> content = all.getContent();
        model.addAttribute("book",content);
        model.addAttribute("page",1);
        if(all.getTotalPages()==0){
            model.addAttribute("count",1);
        }
        else{
            model.addAttribute("count",all.getTotalPages());
        }

        return "adminbooklist";

    }
    @GetMapping("/admin/book/register")
    public String reg(@AuthenticationPrincipal PrincipalDetails userDetails,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        return "bookregister";
    }
    @PostMapping("/admin/book/register")
    public String itemregister(Book book){
        bookService.register(book);
        return "redirect:/admin/booklist";
    }
    @GetMapping("/admin/book/page")
    public String pages(@AuthenticationPrincipal PrincipalDetails userDetails,int page,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e) {
            model.addAttribute("check", false);
        }

        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Book> all = bookRepository.findAll(pageRequest);
        List<Book> content = all.getContent();
        model.addAttribute("book",content);
        model.addAttribute("page",page);
        if(all.getTotalPages()==0){
            model.addAttribute("count",1);
        }
        else{
            model.addAttribute("count",all.getTotalPages());
        }

        return "adminbooklist";
    }
    @GetMapping("/admin/search/book")
    public String adminsearchbook(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e) {
            model.addAttribute("check", false);
        }
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> byNameContaining = bookRepository.findByNameContaining(search, pageRequest);
        List<Book> content = byNameContaining.getContent();
        model.addAttribute("search",search);
        model.addAttribute("book",content);
        model.addAttribute("page",1);
        model.addAttribute("count",byNameContaining.getTotalPages());

        return "adminsearchbook";
    }
    @GetMapping("/admin/search/book/page")
    public String adminsearchbookpage(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model,int page){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Book> byNameContaining = bookRepository.findByNameContaining(search, pageRequest);
        List<Book> content = byNameContaining.getContent();
        model.addAttribute("search",search);
        model.addAttribute("book",content);
        model.addAttribute("page",page);
        model.addAttribute("count",byNameContaining.getTotalPages());

        return "adminsearchbook";
    }
    @GetMapping("/admin/book/update")
    public String updataitem(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model,Long id){
        try {
            model.addAttribute("name", userDetails.getUser().getName());
            model.addAttribute("check", true);
        } catch (NullPointerException e) {
            model.addAttribute("check", false);
        }
        model.addAttribute("id",id);
        return "bookupdate";
    }
    @PostMapping("/admin/book/update")
    public String update(Long id,Book book){
        bookService.update(id,book);
        return "redirect:/admin/booklist";
    }
    @GetMapping("/admin/book/delete")
    public String delete(@AuthenticationPrincipal PrincipalDetails userDetails,Long id){
        bookService.delete(id);

        return "redirect:/admin/booklist";

    }




}
