package woo.woot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest req, Model model) {
        log.info("home controller access");

        HttpSession session = req.getSession();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("member") != null) {
            log.info("get : " + ((MemberForm) session.getAttribute("member")).getName());
            MemberForm memberForm = (MemberForm) session.getAttribute("member");
            model.addAttribute("memberForm", memberForm);
        }
        return "home";
    }

}
