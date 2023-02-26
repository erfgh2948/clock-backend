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
		if(token>=1) { //token != null && 추가
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
		// 패킷을 주고받는 중에 뭔가가 문제가 발생해서
		// 백엔드랑 DB랑 연결이 끊긴것같아요
		//흠... DB테이블쪽 확인해볼까?
		// 아니 그러기엔 다른 데이터들 불러오는거 있긴했잖아
		// 저 페이지도 db데이터 아니요? 맞는데 와인카드 테이블이 있어서 그게 추가가 안된거가 해서그 
		// 이런식으로 해야하나? 자바도 형 좀 까다롭게 따지나 글쎄.. 이거 한번 해볼까? 이대로? GIT에 올려?넹
		// 이건 좀 많이 까다롭네용ㄷㄷㄷ 그러게..'흠.. 다음에 할까? 오늘 많이 늦었는데?
		// 하나만
		// 저거 로그에는 왜 아직도 74번줄이지? 아까 주석단거까지 해서 80번줄 근처로 찍혀야하는거 아닌가 잠만
		if (token>=1) {
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
	

