package Myshop.shop.controller;

import Myshop.shop.config.auth.PrincipalDetails;
import Myshop.shop.entity.User;
import Myshop.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {


    private final UserService userService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails userDetails, Model model){
        try{
            if(userService.getadd(userDetails.getUser().getId())){
                model.addAttribute("address",true);
            }
            else{
                model.addAttribute("address",false);

            }

            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);

        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }

        return "index";

    }

    @GetMapping("/joinform")
    public String join(){
        return "joinform";
    }

    @GetMapping("/loginform")
    public String login(){
        return "loginform";
    }

    @PostMapping("/join")
    public String joinuser(User user){
        userService.join(user);
        return "redirect:/";
    }
    @GetMapping("/address")
    public String addressrg(@AuthenticationPrincipal PrincipalDetails userDetails, Model model){
        try{
            model.addAttribute("name",userDetails.getUser().getName());
            model.addAttribute("check",true);
            if(userDetails.getUser().getAddress()==null){
                model.addAttribute("address",true);
            }
        }catch (NullPointerException e){
            model.addAttribute("check",false);
        }

        return "addressrg";

    }
    @PostMapping("/address")
    public String address(@AuthenticationPrincipal PrincipalDetails userDetails,String address){
        String userid = userDetails.getUser().getUserid();
        userService.setadd(userid,address);
        return "redirect:/";
    }


}
