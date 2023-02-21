package com.ex.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="winescart")
public class WineCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @OneToOne//회원과 1:1관계. 회원은 하나의 장바구니를 가진다.
//    @JoinColumn(name = "member_id")
    @Column
    private Integer memberId;
//    private Member member;
    
    
//    @ManyToOne//하나의 장바구니엔 여러 상품이 있을수있다.
//    @JoinColumn(name = "wine_id")
    @Column
    private Integer wineId;
//    private Wine wine;
    
    
}