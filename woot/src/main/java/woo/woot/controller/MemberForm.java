package woo.woot.controller;

import lombok.Getter;
import lombok.Setter;
import woo.woot.domain.SexType;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {


    @NotEmpty(message = "아이디 입력은 필수입니다.")
    private String username;
    @NotEmpty(message = "이름 입력은 필수입니다.")
    private String name;
    @NotEmpty(message = "패스워드 입력은 필수입니다.")
    private String password;

    private SexType sex;
}
