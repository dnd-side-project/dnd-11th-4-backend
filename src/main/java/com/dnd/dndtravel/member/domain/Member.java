package com.dnd.dndtravel.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private SelectedColor selectedColor; // 유저가 선택한 컬러

    @Builder
    private Member(String name, String email, SelectedColor selectedColor) {
        this.name = name;
        this.email = email;
        this.selectedColor = selectedColor;
    }

    public static Member of(String userName, String email, String selectedColor) {
        return Member.builder()
            .name(userName)
            .email(email)
            .selectedColor(SelectedColor.convertToEnum(selectedColor))
            .build();
    }
}
