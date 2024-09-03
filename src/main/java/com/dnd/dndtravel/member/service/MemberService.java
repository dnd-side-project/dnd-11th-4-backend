package com.dnd.dndtravel.member.service;

import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String name, String email, String selectedColor) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.of(name, email,selectedColor)));
    }

    @Transactional
    public void withdrawMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepository.delete(member);
    }
}
