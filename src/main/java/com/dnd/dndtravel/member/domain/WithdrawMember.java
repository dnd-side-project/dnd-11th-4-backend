package com.dnd.dndtravel.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "apple_id")
	}
)
@Entity
public class WithdrawMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String appleId;

	@Column(nullable = false)
	private String email;

	private WithdrawMember(String appleId, String email) {
		this.appleId = appleId;
		this.email = email;
	}

	public static WithdrawMember of(String appleId, String email) {
		return new WithdrawMember(appleId, email);
	}
}
