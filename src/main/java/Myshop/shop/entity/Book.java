package Myshop.shop.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

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

    @OneToOne


}
