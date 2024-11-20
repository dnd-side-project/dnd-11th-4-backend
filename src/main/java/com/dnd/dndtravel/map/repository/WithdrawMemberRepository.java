package com.dnd.dndtravel.map.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.member.domain.WithdrawMember;

public interface WithdrawMemberRepository extends JpaRepository<WithdrawMember, Long> {
	Optional<WithdrawMember> findByAppleId(String sub);

	WithdrawMember findByEmail(String email);

	boolean existsByAppleId(String sub);
}
