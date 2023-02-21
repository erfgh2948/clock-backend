package com.ex.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ex.backend.entity.Wine;
import com.ex.backend.entity.WineCart;
import com.ex.backend.repository.WineCartRepository;
import com.ex.backend.repository.WineRepository;


@RestController
public class WineController {
	
	@Autowired
	private WineRepository wineRepository;
	@Autowired
	private WineCartRepository wineCartRepository;
	
	@GetMapping("/api/wineList")//와인리스트 전부
	public ResponseEntity getWine(@CookieValue(value="token",required=false) Integer token){
		
		List<Wine> wines=wineRepository.findAll();
		return new ResponseEntity<>(wines,HttpStatus.OK);
	};
	
	@PostMapping("/api/cart/wines")//와인 id들로 와인 정보 반환
	public ResponseEntity getWinesByNumbers(@RequestBody Integer[] mapnum,@CookieValue(value="token",required=false) Integer token){
		List<Wine> wines= wineRepository.findByNumber(0);
		 for(int i=0;i<mapnum.length;i++) {
			 wines.addAll(wineRepository.findByNumber(mapnum[i]));
		 }
			
//		List<Wine> wines=wineRepository.findByNumber();
		return new ResponseEntity<>(wines,HttpStatus.OK);
	}

	@PostMapping("/api/cart/winesincart")//와인을 장바구니에 추가요청. 장바구니에 저장한다.중복저장시 그냥됨. 수정필요.
	public ResponseEntity saveWineCartData(@RequestBody WineCart dto,@CookieValue(value="token",required=false) Integer token){
		System.out.println("hello");
		if(token>=1) {
		WineCart newWineCart = new WineCart();
		newWineCart.setMemberId(token);
		newWineCart.setWineId(dto.getWineId());
		wineCartRepository.save(newWineCart);
		return new ResponseEntity<>(HttpStatus.OK);
		}
		else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/api/cart/winesincartdelete")//와인을 장바구니에서 삭제한다.
	public ResponseEntity deleteWineCartData(@RequestBody WineCart dto,@CookieValue(value="token",required=false) Integer token){
				System.out.println("hi");
		if(token>=1) {
		wineCartRepository.deleteAll(wineCartRepository.findAllByMemberIdAndWineId(token,dto.getWineId()));//중복저장을 허용했기때문에 memberId와 요청된 wineId에 매칭되는 모든 row 삭제
		return new ResponseEntity<>(HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/cart/winesincart")//해당 회원의 장바구니에 있는 와인 id를 모두 리턴
	public ResponseEntity getWineCartData(@CookieValue(value="token",required=false) Integer token){
		
		if(token>=1) {
		List<WineCart> winecart = wineCartRepository.findAllByMemberId(token);//memberID로 모든 row 검색
		return new ResponseEntity<>(winecart,HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
//	@Transactional
//	@PostMapping("/api/orders")//새로운 주문 
//	public ResponseEntity pushOrder(@RequestBody OrderDto dto, @CookieValue(value="token",required=false) Integer token){
//		
//		Order newOrder = new Order();
//		newOrder.setMemberId(token);
//		newOrder.setName(dto.getName());//비어있을수 있으므로 유효성검사도 해야하나 일단 썼음.
//		newOrder.setAddress(dto.getAddress());
//		newOrder.setPayment(dto.getPayment());
//		newOrder.setCardNumber(dto.getCardNumber());
//		newOrder.setItems(dto.getItems());
//		
//		
//		orderRepository.save(newOrder);
//		cartRepository.deleteByMemberId(token);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	

