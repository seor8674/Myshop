package Myshop.shop.entity;

import lombok.Data;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String name;

    private String Author;
    private String content;
    private int price;
    private int quantity;

    @OneToMany(mappedBy = "book")
    List<Order> orderList = new ArrayList<>();


}
