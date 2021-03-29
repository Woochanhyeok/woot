package woo.woot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woo.woot.domain.Dtype;
import woo.woot.domain.Item;
import woo.woot.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        //디폴트로 최신순으로 정렬
        Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i2).getRegisterDate().compareTo(((Item)i1).getRegisterDate()); } });
        model.addAttribute("items", items);


        return "home";
    }

    @PostMapping("/dtype")
    public String sort(HttpServletRequest req, Model model, @RequestParam("dtype")String dtype, @RequestParam("sort")String sort) {

        model.addAttribute("dtype", dtype);
        model.addAttribute("sort", sort);
        log.info("home   //   dtype : " + dtype);
        log.info("home   //   sort : " + sort);

        HttpSession session = req.getSession();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("memberForm") != null) {
            log.info("get : " + ((MemberForm) session.getAttribute("memberForm")).getName());
            MemberForm memberForm = (MemberForm) session.getAttribute("memberForm");
            model.addAttribute("memberForm", memberForm);
        }

        //전체 아이템 리스트 dtype 별로 뽑아오기
        List<Item> items = new ArrayList<>();
        if(dtype.equals(Dtype.HEADWEAR.toString())) {
            items = itemService.findItemsByDtype(Dtype.HEADWEAR);
        } else if(dtype.equals(Dtype.TOP.toString())) {
            items = itemService.findItemsByDtype(Dtype.TOP);
        } else if(dtype.equals(Dtype.PANTS.toString())) {
            items = itemService.findItemsByDtype(Dtype.PANTS);
        } else if(dtype.equals(Dtype.OUTER.toString())) {
            items = itemService.findItemsByDtype(Dtype.OUTER);
        } else if(dtype.equals(Dtype.SHOES.toString())) {
            items = itemService.findItemsByDtype(Dtype.SHOES);
        } else {
            items = itemService.findItems();
        }

        //전체 아이템 리스트 sort 값에 따라 정렬 new hot review low high  new는 그대로
        if(sort.equals("new")) {
            Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i2).getRegisterDate().compareTo(((Item)i1).getRegisterDate()); } });

        } else if(sort.equals("hot")) {
            Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i2).getOrder_count()-((Item)i1).getOrder_count(); } });
        } else if(sort.equals("review")) {
            Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i2).getReviews().size()-((Item)i1).getReviews().size(); } });
        } else if(sort.equals("low")) {
            Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i1).getPrice()-((Item)i2).getPrice(); } });
        } else {             //high
            Collections.sort(items, new Comparator() { @Override public int compare(Object i1, Object i2) { return ((Item)i2).getPrice()-((Item)i1).getPrice(); } });
        }

        model.addAttribute("items", items);

        return "home";
    }
}
