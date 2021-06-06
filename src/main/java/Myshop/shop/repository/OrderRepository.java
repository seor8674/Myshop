package Myshop.shop.repository;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
