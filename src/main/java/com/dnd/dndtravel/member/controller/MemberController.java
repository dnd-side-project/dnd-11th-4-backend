package com.dnd.dndtravel.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.member.service.MemberService;
import com.dnd.dndtravel.member.service.response.MyPageResponse;
import com.dnd.dndtravel.member.swagger.MemberControllerSwagger;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberController implements MemberControllerSwagger {

	private final MemberService memberService;

	@GetMapping("/mypages")
	public MyPageResponse myPage(AuthenticationMember authenticationMember) {
		return memberService.myPageInfo(authenticationMember.id());
	}
}
