package com.ex.backend.entity;

import java.time.LocalDateTime;

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
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private int memberId;
    
    @Column(length = 200, nullable= false)
    private String name;
    
    @Column(length = 500, nullable= false)
    private String address;

    
    @Column(length = 10, nullable= false)
    private String payment;
    
    @Column(length=16)
    private String cardNumber;
    
    @Column(length=500, nullable=false)
    private String items;
}