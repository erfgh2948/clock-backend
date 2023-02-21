package com.ex.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ex.backend.entity.Cart;
import com.ex.backend.entity.Items;
import com.ex.backend.entity.Member;
import com.ex.backend.repository.CartRepository;
import com.ex.backend.repository.ItemRepository;
import com.ex.backend.repository.MemberRepository;

@RestController
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/api/cart/items")//현재 카트 불러오기
	public ResponseEntity getCartItems(@CookieValue(value="token",required=false) String token) {
		if(token==null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Optional<Member> member=memberRepository.findById(Integer.parseInt(token));
		int memberId=member.get().getId();
		List<Cart> carts=cartRepository.findByMemberId(memberId); //cart's table of a user's
		List<Integer> itemIds=carts.stream().map(Cart::getItemId).toList();//extract cart's itemIDS from
		
		List<Items> items = itemRepository.findByIdIn(itemIds);
		
		
		
		return new ResponseEntity<>(carts,HttpStatus.OK);
	}
	
	@PostMapping("/api/cart/items/{itemId}")//카트에 아이템 추가
	public ResponseEntity pushCartItem(@PathVariable("itemId") int itemId, @CookieValue(value="token",required=false) String token){//itemId와 userid를 arg로 받는다.
		if(token==null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Member> member=memberRepository.findById(Integer.parseInt(token));
		Items items = itemRepository.findById(itemId);
		int memberId=member.get().getId();
		Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);//요청된 arg들로 검색했을때 있는아이템이면 객체를 만들어 아래 if를 피한다.
		
		if(cart==null) {//if cart is empty; fill contents with this user's Id 카트에 아이템이 없었다면 담긴다
			System.out.println("비었어요");
			Cart newCart=new Cart();
			newCart.setMemberId(memberId);
			newCart.setPrice(items.getPrice());
			newCart.setImgPath(items.getImgPath());
			newCart.setItemId(itemId);
			cartRepository.save(newCart);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@DeleteMapping("/api/cart/items/{itemId}")//Id로 삭제요청
	public ResponseEntity popCartItem(@PathVariable("itemId") int itemId, @CookieValue(value="token",required=false) String token){
		if(token==null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Member> member=memberRepository.findById(Integer.parseInt(token));
		
		int memberId=member.get().getId();
		Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
		
		cartRepository.delete(cart);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
}