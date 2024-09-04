package com.dnd.dndtravel.auth.service;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MemberNameGenerator {
	private static final String[] ADJECTIVES = {
		"가냘픈", "가엾은", "거센", "거친", "건조한",
		"게으른", "고달픈", "귀여운", "그리운", "깨끗한",
		"누런", "느린", "더러운", "덜된", "동그란", "뛰어난",
		"무서운", "미친", "보람찬", "뽀얀", "비싼", "서툰","섣부른",
  		"성가신","수줍은","쏜살같은","못난", "못생긴", "무거운", "빠른",
		"용감한", "행복한", "슬픈", "탐스러운", "한결같은","희망찬"
	};

	private static final String[] ANIMALS = {
		"사자", "호랑이", "곰", "여우", "토끼",
		"거북이", "고양이", "늑대", "강아지", "팬더",
		"병아리", "망아지", "코끼리", "오징어", "바퀴벌레",
		"거머리", "개미핥기", "돌고래", "순록", "올빼미", "박쥐",
		"펭귄", "나무늘보", "오소리", "하이에나", "햄스터"
	};

	private final Random random = new Random();

	public String generateRandomName() {
		String adjective = ADJECTIVES[this.random.nextInt(ADJECTIVES.length)];
		String animal = ANIMALS[this.random.nextInt(ANIMALS.length)];
		return adjective + animal;
	}
}
