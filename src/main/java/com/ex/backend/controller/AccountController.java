package com.ex.backend.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ex.backend.entity.Member;
import com.ex.backend.repository.MemberRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AccountController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	@PostMapping("/api/account/login")
	public ResponseEntity login(@RequestBody Map<String,String> params,HttpServletResponse res) {
		Member member=memberRepository.findByEmailAndPassword(params.get("email"),params.get("password"));
		if(member!=null) {
			System.out.println(member.getId()+"번회원님 로그인");
//			JwtService jwtService=new JwtServiceImpl();
//			int id=member.getId();//id값 가져와서
//			String token = jwtService.getToken("id", id);//토큰만들기.그냥 쿠키에 id값을 토큰이라 치고 넣음.
			
			String id = member.getId().toString();
			
			Cookie cookie = new Cookie("token", id);//쿠키에 토큰 추가
			cookie.setHttpOnly(true);//자바스크립트로 접근불가능
			cookie.setPath("/");//모든경로에서 접근가능
			res.addCookie(cookie);//서블릿 res에 쿠키추가
			//현재 cookie는 사용중이 아님.
			
			return new ResponseEntity<>(member,HttpStatus.OK); //id 리턴
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/api/account/signup")//signup 콘트롤러
	public ResponseEntity signup(@RequestBody Member member, HttpServletResponse res) {
		System.out.println(member.toString());
		Optional<Member> isExist = memberRepository.findByEmail(member.getEmail());
		
		if(isExist.isEmpty()){//없다면
			Member newmember=new Member();
			newmember.setEmail(member.getEmail());
			newmember.setPassword(member.getPassword());
			newmember.setName(member.getName());
			newmember.setAddress(member.getAddress());
			memberRepository.save(newmember);//저장
			System.out.println("가입");
			return  new ResponseEntity<>(newmember, HttpStatus.OK);
		}
		
		else return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
