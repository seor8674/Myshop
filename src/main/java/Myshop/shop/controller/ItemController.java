package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import Myshop.shop.entity.Post;
import Myshop.shop.entity.User;
import Myshop.shop.repository.BookRepository;
import Myshop.shop.repository.OrderRepository;
import Myshop.shop.repository.UserRepository;
import Myshop.shop.service.BookService;
import Myshop.shop.service.OrderService;
import Myshop.shop.service.UserService;
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
    @Autowired
    BookService bookService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

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
    public String order(@AuthenticationPrincipal PrincipalDetails userDetails,Long id,int count){
        Long userid = userDetails.getUser().getId();
        User user = userRepository.findById(userid).get();
        Book book = bookService.sellbook(id, count);
        Order order = new Order(count);
        order.setPrice(book.getPrice()*count);
        orderService.join(order,book);
        userService.addorder(order,user);
        return "redirect:/itemlist";
    }
    @GetMapping("/myorderlist")
    public String orderlist(@AuthenticationPrincipal PrincipalDetails userDetails,Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        User user = userRepository.findById(userDetails.getUser().getId()).get();
        List<Order> byUser = orderRepository.findByUser(user);

        model.addAttribute("ol",byUser);
        return "myorderlist";
    }
    @GetMapping("/search/book")
    public String searchbook(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> byNameContaining = bookRepository.findByNameContaining(search, pageRequest);
        List<Book> content = byNameContaining.getContent();
        model.addAttribute("search",search);
        model.addAttribute("book",content);
        model.addAttribute("page",1);
        model.addAttribute("count",byNameContaining.getTotalPages());

        return "searchbook";
    }
    @GetMapping("/book/page")
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

        return "itemlist";
    }
    @GetMapping("/search/book/page")
    public String searchbookpage(@AuthenticationPrincipal PrincipalDetails userDetails,String search, Model model,int page){
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

        return "searchbook";
    }

}
