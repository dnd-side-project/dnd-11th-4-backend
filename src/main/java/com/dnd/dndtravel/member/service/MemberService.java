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
        // 재가입 유저
        log.info("email = {}", email);
        log.info("sub = {}", sub);
        validateNewMemberEmail(email, sub);
        String targetEmail = determineEmail(email, sub);
        if (targetEmail.equals(email)) {
            withDrawMemberRepository.save(WithdrawMember.of(sub, email));
        }
        return memberRepository.save(
            Member.of(
                memberNameGenerator.generateRandomName(),
                targetEmail,
                selectedColor
            )
        );
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
        log.info("sub = {}" + sub);
        return withDrawMemberRepository.findByAppleId(sub)
            .orElseThrow(() -> new MemberNotFoundException(sub))
            .getEmail();
    }

    private String determineEmail(String email, String sub) {
        // 케이스 1: 탈퇴 후 재가입 유저 (이메일 없음)
        if (email == null) {
            return getWithdrawMemberEmail(sub);
        }

        // 케이스 2, 4: 이메일이 있는 경우
        WithdrawMember withdrawMember = withDrawMemberRepository.findByEmail(email);
        return withdrawMember.getEmail() == null ? email : withdrawMember.getEmail();
    }

    private void validateNewMemberEmail(String email, String sub) {
        // 최로 로그인 유저인데 IdToken의 email도 null인경우 (케이스 3)
        if (email == null && withDrawMemberRepository.existsByAppleId(sub)) {
            throw new MemberNotFoundException(sub);
        }
    }
}
