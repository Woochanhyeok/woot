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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private HttpSession session;

    @GetMapping("/members/signin")
    public String signInForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/signin";
    }

    @PostMapping("/members/signin")
    public String signIn(MemberForm memberForm, HttpServletRequest req) {
        log.info("signin : " + memberForm.getUsername() + " " + memberForm.getPassword());
        boolean isMember = memberService.signInCheck(memberForm.getUsername(), memberForm.getPassword());
        if(!isMember){
            return "/members/signin";

        }
        memberForm.setName(memberService.findNameByUsername(memberForm.getUsername()));     //username으로 name 찾아서 넣어줌
        HttpSession session = req.getSession();
        session.setAttribute("member", memberForm);

        return "redirect:/";
    }

    @GetMapping("/members/signup")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/signup";
    }

    @PostMapping("/members/signup")
    public String create(@Valid MemberForm memberForm, HttpServletRequest req, BindingResult result) {

        if (result.hasErrors()) {
            return "members/signup";
        }

        log.info("id : " + memberForm.getUsername() + "password : " + memberForm.getPassword() + "name : " + memberForm.getName() + "sex : " + memberForm.getSex());

        Member member = new Member();
        member.setUsername(memberForm.getUsername());
        member.setPassword(memberForm.getPassword());
        member.setName(memberForm.getName());
        member.setSex(memberForm.getSex());

        memberService.join(member);

        //세션
        session = req.getSession();
        session.setAttribute("member", memberForm);

        //세션 로그 출력
        log.info(session.getId());
        log.info(((MemberForm) session.getAttribute("member")).getName());

        return "redirect:/";
    }

    @GetMapping("/members/signout")
    public String signOut(HttpSession session) {
        session.removeAttribute("member");

        return "redirect:/";
    }
}
