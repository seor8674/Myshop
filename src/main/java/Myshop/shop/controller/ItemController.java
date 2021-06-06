package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.Book;
import Myshop.shop.entity.Post;
import Myshop.shop.entity.User;
import Myshop.shop.repository.BookRepository;
import Myshop.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/itemlist")
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

        return "itemlist";

    }
    @GetMapping("/book/details")
    public String itemdetail(@AuthenticationPrincipal PrincipalDetails userDetails,Long id, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        Optional<Book> byId = bookRepository.findById(id);
        Book book = byId.get();
        model.addAttribute("book",book);

        return "itemdetails";

    }
    @PostMapping("/order")
    public String order(@AuthenticationPrincipal PrincipalDetails userDetails,int count){
        Long id = userDetails.getUser().getId();
        User user = userRepository.findById(id).get();


    }

}
