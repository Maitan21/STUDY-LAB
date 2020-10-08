package com.dxc.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//회원가입을 할때 받아올 데이터
@Data
public class SignUpForm {



    // sing-up.html 에서 받아오는 Data
    @NotBlank
    @Length(min = 3, max =20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email //Email 형식
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max =50) //자릿수
    private String password;

}
