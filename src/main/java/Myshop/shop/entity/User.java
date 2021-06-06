package Myshop.shop.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    private String name;
    private String address;
    private String userid;
    private String password;
    private String email;
    private String role;

    public User(String name, String address, String userid, String password,String email) {
        this.name = name;
        this.address = address;
        this.userid = userid;
        this.password = password;
        this.email=email;
        if(userid.contains("admin")){
            this.role= "ROLE_ADMIN";
        }
        else{
            this.role="ROLE_USER";
        }
    }

    @OneToMany(mappedBy = "user")
    List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Order> orderList = new ArrayList<>();

    public void addorder(Order order){
        this.orderList.add(order);
        order.setUser(this);
    }


}
