package Myshop.shop.service;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Order;
import Myshop.shop.repository.BookRepository;
import Myshop.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookRepository bookRepository;

    public void join(Order order, Book book){
        order.setBook(book);
        orderRepository.save(order);

    }
    public void delete(Long id){
        Order order = orderRepository.findById(id).get();
        int count = order.getCount();
        Book book = order.getBook();
        Book book1 = bookRepository.findById(book.getId()).get();
        book1.setQuantity(book1.getQuantity()+count);
        orderRepository.delete(order);
    }

}
