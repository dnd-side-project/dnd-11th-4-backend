package com.dnd.dndtravel.map.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberAttraction_id")
	private MemberAttraction memberAttraction;

	private String url;

	private Photo(MemberAttraction memberAttraction, String url) {
		this.memberAttraction = memberAttraction;
		this.url = url;
	}

	public static Photo of(MemberAttraction memberAttraction, String imageUrl) {
		return new Photo(memberAttraction, imageUrl);
	}
}
