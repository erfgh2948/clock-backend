package com.ex.backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer>{//entity type, PK type

	List<Cart> findByMemberId(int memberId);
	
	Cart findByMemberIdAndItemId(int memberId, int itemId);
	
	void deleteByMemberId(int memberId);
}

