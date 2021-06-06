package Myshop.shop.repository;

import Myshop.shop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    public Page<Post> findAll(Pageable pageable);

    public Page<Post> findByTitleContaining(String title,Pageable pageable);
}
