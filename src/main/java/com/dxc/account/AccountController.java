package com.dxc.account;

import com.dxc.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor //이 이노테이션 설명 필요함
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;


    @InitBinder("signUpForm") //바인더 설정
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm",new SignUpForm());
        return "account/sign-up";
    }

    //회원가입 post 받기
    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "account/sign-up";
        }

        //Account 객체를 만들어서 폼 내용 저장
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(signUpForm.getPassword()) // TODO Need to Password encoding
                .studyCreateByWeb(true)
                .studyEnrolmentResultByWeb(true)
                .studyUpdatedByWeb(true)
                .build();

        Account newAccount = accountRepository.save(account);

        //토큰 생성
        newAccount.generateEmailCheckToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("Hello! This is DXC Email Validate");
        mailMessage.setText("/check-email-token?token=" + newAccount.getEmailCheckToken() + "&email="
                + newAccount.getEmail()); // 본문
        javaMailSender.send(mailMessage);


        //TODO 회원 가입 처리
        return "redirect:/";
    }


}
