package com.dnd.dndtravel.member.service;

import com.dnd.dndtravel.apple.dto.AppleUser;
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
    public Member saveMember(AppleUser appleUser) {
        return memberRepository.findByEmail(appleUser.getEmail())
                .orElseGet(() -> memberRepository.save(Member.of(appleUser)));
    }
}
