package Myshop.shop.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="orders")
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    private int count;
    private int price;
    public Order(int count){
        this.count=count;
    }

    public void setBook(Book book) {
        this.book = book;
        book.getOrderList().add(this);
    }
}
