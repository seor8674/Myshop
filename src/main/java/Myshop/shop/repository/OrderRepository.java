package Myshop.shop.repository;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import Myshop.shop.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("select o from Order o where o.user= :user")
    public List<Order> findByUser(@Param("user") User user);
}
