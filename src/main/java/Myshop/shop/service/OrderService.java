package Myshop.shop.service;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import Myshop.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void join(Order order, Book book){
        order.setBook(book);
        orderRepository.save(order);

    }

}
