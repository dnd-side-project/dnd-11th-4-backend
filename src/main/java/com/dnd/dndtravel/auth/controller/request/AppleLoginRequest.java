package com.dnd.dndtravel.auth.controller.request;

//todo 필드값들 유효성 체크
// 	todo 입력 색상 예시는 클라에서 String 타입들. RED, ORANGE, YELLOW, MELON, BLUE, PURPLE
public record AppleLoginRequest(
	String appleToken,
	String selectedColor
){
}