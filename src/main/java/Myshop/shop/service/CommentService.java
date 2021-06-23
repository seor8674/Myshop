package Myshop.shop.service;

import Myshop.shop.entity.Comment;
import Myshop.shop.entity.Post;
import Myshop.shop.repository.CommentRepository;
import Myshop.shop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void regist(Long postid,String username,String content){
        Comment comment = new Comment(content,username);
        Post post = postRepository.findById(postid).get();
        post.getCommentList().add(comment);
        comment.setPost(post);
        commentRepository.save(comment);

    }
}
