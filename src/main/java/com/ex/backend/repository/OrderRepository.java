package com.ex.backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Items;
import com.ex.backend.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{//entity type, PK type

	List<Order> findByMemberIdOrderByIdDesc(int memberId);
}

