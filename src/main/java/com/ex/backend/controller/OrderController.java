package com.ex.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.backend.Dto.OrderDto;
import com.ex.backend.entity.Order;
import com.ex.backend.repository.CartRepository;
import com.ex.backend.repository.OrderRepository;

@RestController
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartRepository cartRepository;


	@GetMapping("/api/orders")//헤더의 주문내역보기
	public ResponseEntity getOrder(@CookieValue(value="token",required=false) Integer token){
		
		List<Order> orders=orderRepository.findAll();
//		List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId) 역순배치를 위해서
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/api/orders")//새로운 주문 
	public ResponseEntity pushOrder(@RequestBody OrderDto dto, @CookieValue(value="token",required=false) Integer token){
		
		Order newOrder = new Order();
		newOrder.setMemberId(token);
		newOrder.setName(dto.getName());//비어있을수 있으므로 유효성검사도 해야하나 일단 썼음.
		newOrder.setAddress(dto.getAddress());
		newOrder.setPayment(dto.getPayment());
		newOrder.setCardNumber(dto.getCardNumber());
		newOrder.setItems(dto.getItems());
		
		
		orderRepository.save(newOrder);
		cartRepository.deleteByMemberId(token);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

	
}
