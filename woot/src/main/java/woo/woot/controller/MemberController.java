package woo.woot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woo.woot.domain.Member;
import woo.woot.service.MemberService;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/signin")
    public String signInForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/signin";
    }

    @GetMapping("/members/signup")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/signup";
    }

    @PostMapping("/members/signup")
    public String create(@Valid MemberForm memberForm, BindingResult result) {

        if(result.hasErrors()) {
            return "members/signup";
        }

        log.info("id : " + memberForm.getUsername()+ "password : " + memberForm.getPassword()+"name : " + memberForm.getName()+"sex : " + memberForm.getSex());

        Member member = new Member();
        member.setUsername(memberForm.getUsername());
        member.setPassword(memberForm.getPassword());
        member.setName(memberForm.getName());
        member.setSex(memberForm.getSex());

        memberService.join(member);
        return "redirect:/";
    }
}
