package Myshop.shop.service;

import Myshop.shop.entity.Order;
import Myshop.shop.entity.User;
import Myshop.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserRepository userRepository;

    public void join(User user){
        String pwd = user.getPassword();
        String enpwd =bCryptPasswordEncoder.encode(pwd);
        user.setPassword(enpwd);
        userRepository.save(user);
    }
    public void addorder(Order order,User user){
        user.addorder(order);
    }

    public void setadd(String userid,String address){
        User byUserid = userRepository.findByUserid(userid);
        byUserid.setAddress(address);
    }
    public boolean getadd(Long id){
        User user = userRepository.findById(id).get();
        if(user.getAddress()==null){
            return true;
        }
        else{
            return false;
        }
    }

}
