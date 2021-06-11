package Myshop.shop.service;

import Myshop.shop.entity.Book;
import Myshop.shop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book sellbook(Long id, int count){
        Book book = bookRepository.findById(id).get();
        book.setQuantity(book.getQuantity()-count);
        return book;
    }
    public void register(Book book){
        bookRepository.save(book);
    }
}
