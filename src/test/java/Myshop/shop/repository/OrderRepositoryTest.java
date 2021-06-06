package Myshop.shop.repository;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import Myshop.shop.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void test(){
        User user = new User("이판", "서울", "oewqe123", "123123", "dawd@adad.com");
        userRepository.save(user);
        Book book =null;
        Order order = new Order(3);



    }
}