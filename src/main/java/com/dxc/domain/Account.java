package com.dxc.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    //Account 도메인에 필요한 데이터
    // 로그인 / 프로필 / 알림 설정

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    //이메일 인증 여부
    private boolean emailVerified;

    //이메일 검증 시 사용할 토큰 값
    private String emailCheckToken;

    //가입 날짜
    private LocalDateTime joinedAt;

    /* == 기본 적인 프로필 == */
    private String bio;

    private String url;

    private String occupation;

    private String location; // varchar(255)

    // 문자열이 초과할때 이노테이션이 처리
    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    // 알림설정
    private boolean studyCreateByEmail;

    private boolean studyCreateByWeb;

    private boolean studyEnrolmentResultByEmail;

    private boolean studyEnrolmentResultByWeb;

    //갱신 결과
    private boolean studyUpdatedByWeb;

    private boolean studyUpdateByEmail;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }
}
