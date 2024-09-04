package com.dnd.dndtravel.member.service;

import com.dnd.dndtravel.auth.service.MemberNameGenerator;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberNameGenerator memberNameGenerator;

    @Transactional
    public Member saveMember(String email, String selectedColor) {
        String name = memberNameGenerator.generateRandomName();
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.of(name, email,selectedColor)));
    }
}
