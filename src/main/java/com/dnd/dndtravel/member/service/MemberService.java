package com.dnd.dndtravel.member.service;

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
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberAttractionRepository memberAttractionRepository;
    private final MemberRegionRepository memberRegionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final WithdrawMemberRepository withDrawMemberRepository;
    private final MemberNameGenerator memberNameGenerator;

    /**
     * 1. 탈퇴후 재가입 유저의 email이 null인경우
     * => withDraw 테이블을 조회후 최초 로그인시 저장했던 sub값으로 유저 판별후 sub값에 해당하는 email 가져와 DB에 저장
     *
     * 2. 탈퇴후 재가입 유저의 email이 존재하는경우
     * => 해당 email로 withDraw 테이블을 조회후, 해당하는 email 가져와 DB에 저장
     *
     * 3. 최초 로그인 유저의 email이 null인경우
     * => 가입 시켜줄수 없음 (예외)
     *
     * 4. 최초 로그인 유저의 email이 존재하는경우
     * => 해당 email로 withDraw 테이블 조회후, email존재여부를 확인하고 없으므로 입력받은 email을 DB에 저장
     */
    @Transactional
    public Member saveMember(String email, String sub, String selectedColor) {
        log.info("email = {}", email);
        log.info("sub = {}", sub);
        WithdrawMember existingMember = withDrawMemberRepository.findByAppleId(sub);
        // 새 유저
        if (existingMember == null) {
            return handleNewMember(email, sub, selectedColor);
        }

        // 가입이력 있던 유저는 withDraw에 있던 정보로 회원테이블에 저장하면 됨, private public 상관없이 최초 로그인하던 이메일 저장
        return memberRepository.save(Member.of(
            memberNameGenerator.generateRandomName(),
            existingMember.getEmail(),
            selectedColor
            ));
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

    private Member handleNewMember(String email, String sub, String selectedColor) {
        // idToken의 email이 null 인경우, 예외 => 일반적으로 있을 수 없는 상황
        if (email == null) {
            throw new MemberNotFoundException(sub);
        }
        // 새 유저의 idToken email을 DB에 저장
        withDrawMemberRepository.save(WithdrawMember.of(sub, email));
        return memberRepository.save(Member.of(
            memberNameGenerator.generateRandomName(),
            email,
            selectedColor
        ));
    }
}
