package woo.woot.controller;

import lombok.Getter;
import lombok.Setter;
import woo.woot.domain.SexType;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewForm {
    private String username;
    private SexType sex;
    private String content;
    private LocalDateTime reviewDate;
}
