package Myshop.shop.service;


import Myshop.shop.entity.Post;
import Myshop.shop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post detail(Long id){
        Post post = postRepository.findById(id).get();
        post.addHit();
        return post;
    }
    public void delete(Long id){
        postRepository.deleteById(id);

    }
    public void update(Long id,String title,String content){
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setContent(content);
    }
}
