package com.ex.backend.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.backend.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Integer>{//entity type, PK type
	Member findByEmailAndPassword(String email,String password);
	Optional<Member> findByEmail(String email);
	
}

