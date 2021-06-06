package Myshop.shop.repository;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUserid(String userid);
}
