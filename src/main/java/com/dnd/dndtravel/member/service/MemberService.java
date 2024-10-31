package com.dnd.dndtravel.member.service;

import java.util.Optional;

import com.dnd.dndtravel.auth.repository.RefreshTokenRepository;
import com.dnd.dndtravel.map.exception.MemberNotFoundException;
import com.dnd.dndtravel.map.repository.MemberAttractionRepository;
import com.dnd.dndtravel.map.repository.MemberRegionRepository;
import com.dnd.dndtravel.auth.service.MemberNameGenerator;
import com.dnd.dndtravel.map.repository.WithdrawMemberRepository;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.domain.WithdrawMember;
import com.dnd.dndtravel.member.repository.MemberRepository;
import com.dnd.dndtravel.member.service.response.MyPageResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAttractionRepository memberAttractionRepository;
    private final MemberRegionRepository memberRegionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final WithdrawMemberRepository withDrawMemberRepository;
    private final MemberNameGenerator memberNameGenerator;

    @Transactional
    public Member saveMember(String email, String sub, String selectedColor) {
        // 재가입 유저
        String targetEmail = Optional.ofNullable(email).orElseGet(() -> getWithdrawMemberEmail(sub));

        if (isNewLoginMember(email)) {
            withDrawMemberRepository.save(WithdrawMember.of(sub));
        }

        return memberRepository.findByEmail(targetEmail)
                .orElseGet(() -> memberRepository.save(Member.of(memberNameGenerator.generateRandomName(), targetEmail,selectedColor)));
    }

    @Transactional
    public void withdrawMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepository.delete(member);
        memberAttractionRepository.deleteById(memberId);
        memberRegionRepository.deleteById(memberId);
        refreshTokenRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public MyPageResponse myPageInfo(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        return new MyPageResponse(member.getName());
    }

    private String getWithdrawMemberEmail(String sub) {
        return withDrawMemberRepository.findByAppleId(sub)
            .orElseThrow(() -> new MemberNotFoundException(sub))
            .getEmail();
    }

    private boolean isNewLoginMember(String email) {
        return email != null;
    }

}
