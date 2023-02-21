package com.ex.backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Cart;
import com.ex.backend.entity.Wine;


public interface WineRepository extends JpaRepository<Wine, Integer>{//entity type, PK type

	List<Wine> findByNumber(int Number);
	
}

