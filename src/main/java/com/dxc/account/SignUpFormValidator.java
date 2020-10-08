package com.dxc.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//빈과 빈들만 주입성을 의존받을 수 있다.
//Spring Validator
@Component
@RequiredArgsConstructor
public class  SignUpFormValidator implements Validator {

    //@RequiredArgsConstructor는 private final 생성자를 만들어 준다.
    private  final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        //SignUpForm 인스턴스를 검사
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        //TODO email, nickname 중복 검사
        SignUpForm signUpForm = (SignUpForm)obj;
        if (accountRepository.existsByEmail(signUpForm.getEmail())) {
            //여기부분을 수정하면 다국화 가능
            errors.rejectValue("email","invalid.email", new Object[]{signUpForm.getEmail()},"이미 사용중인 이메일 입니다.");
        }

        if (accountRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname","invalid.nickname", new Object[]{signUpForm.getEmail()},"이미 사용중인 닉네임 입니다.");

        }

    }

}
