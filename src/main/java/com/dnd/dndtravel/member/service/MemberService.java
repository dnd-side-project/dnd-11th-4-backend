package com.dnd.dndtravel.member.service;

import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String name, String email) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.from(name, email)));
    }
}
