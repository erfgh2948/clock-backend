package com.ex.backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Wine;
import com.ex.backend.entity.WineCart;


public interface WineCartRepository extends JpaRepository<WineCart, Integer>{//entity type, PK type

	List<WineCart> findAllByMemberId(int memberid);
	List<WineCart> findAllByMemberIdAndWineId(int memberId,int wineId);
	
}

