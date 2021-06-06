package Myshop.shop.repository;

import Myshop.shop.entity.Book;
import Myshop.shop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

    public Page<Book> findAll(Pageable pageable);

    public Page<Book> findByNameContaining(String title,Pageable pageable);
}
