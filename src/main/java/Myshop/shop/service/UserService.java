package Myshop.shop.service;

import Myshop.shop.entity.Order;
import Myshop.shop.entity.User;
import Myshop.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    public void join(User user){
        String pwd = user.getPassword();
        String enpwd =bCryptPasswordEncoder.encode(pwd);
        user.setPassword(enpwd);
        userRepository.save(user);
    }
    public void addorder(Order order,User user){
        user.addorder(order);
    }



}
