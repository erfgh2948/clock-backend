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
@Table(name="wines")
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column
    private String imgPath;
    
    @Column
    private String variety;

    @Column
    private String winename;
    
    @Column
    private String nation;
    
    @Column
    private int price;
    
    @Column
    private String matching;
    
    @Column
    private int sugar;
    
    @Column
    private int acidity;
    
    @Column
    private int texture;
    
    @Column
    private int tannin;
    
    @Column
    private String scent;
    
    @Column
    private String producer;
    
    @Column
    private String salesVolume;
    
    
}