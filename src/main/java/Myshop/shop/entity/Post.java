package Myshop.shop.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    List<Comment> commentList =new ArrayList<>();

    private int hit;
    private String title;
    private String content;

    public Post(String title, String content) {

        this.title = title;
        this.content = content;
    }

    public void setUser(User user){
        this.user=user;
        user.getPostList().add(this);
    }

    public void addHit() {
        this.hit++;
    }



}
