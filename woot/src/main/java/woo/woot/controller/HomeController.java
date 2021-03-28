package woo.woot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woo.woot.domain.Item;
import woo.woot.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins="*", allowedHeaders = "*")
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(HttpServletRequest req, Model model) {
        log.info("home controller access");

        HttpSession session = req.getSession();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("memberForm") != null) {
            log.info("get : " + ((MemberForm) session.getAttribute("memberForm")).getName());
            MemberForm memberForm = (MemberForm) session.getAttribute("memberForm");
            model.addAttribute("memberForm", memberForm);
        }

        //전체 아이템 리스트 모델에 넣기
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);


        return "home";
    }

}
