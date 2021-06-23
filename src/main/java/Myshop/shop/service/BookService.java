package Myshop.shop.service;

import Myshop.shop.entity.Book;
import Myshop.shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book sellbook(Long id, int count){
        Book book = bookRepository.findById(id).get();
        book.setQuantity(book.getQuantity()-count);
        return book;
    }
    public void register(Book book){
        bookRepository.save(book);
    }
    public void update(Long id,Book book){
        Book book1 = bookRepository.findById(id).get();
        book1.setQuantity(book.getQuantity());
        book1.setAuthor(book.getAuthor());
        book1.setPrice(book.getPrice());
        book1.setContent(book.getContent());
        book1.setName(book.getName());
    }
    public void delete(Long id){
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
    }
}
