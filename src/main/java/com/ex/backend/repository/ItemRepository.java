package com.ex.backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Items;


public interface ItemRepository extends JpaRepository<Items, Integer>{//entity type, PK type

	List<Items> findByIdIn(List<Integer> ids);
	Items findById(int id);
}

