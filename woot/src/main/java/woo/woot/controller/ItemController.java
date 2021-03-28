package woo.woot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import woo.woot.domain.Item;
import woo.woot.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/register")
    public String registerPage(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("memberForm") != null) {
            //모델에 멤버폼 넣기
            log.info("member name : " + ((MemberForm) session.getAttribute("memberForm")).getName());
            MemberForm memberForm = (MemberForm) session.getAttribute("memberForm");
            model.addAttribute("memberForm", memberForm);

            //모델에 아이템폼 넣기
            model.addAttribute("itemForm", new ItemForm());
        }
        return "items/register";
    }

    @PostMapping("/items/register")
    public String register(@Valid ItemForm itemForm, BindingResult result, HttpServletRequest req) throws Exception {
        if (result.hasErrors()) {
            return "items/register";
        }
        log.info(req.getSession().getServletContext().getRealPath(""));

        //상품 이미지 파일 저장
        MultipartFile files = itemForm.getFile();
        String fileName = System.currentTimeMillis()+files.getOriginalFilename();
        String filePath = req.getSession().getServletContext().getRealPath("images/"+fileName);
//        String filePath = req.getSession().getServletContext().getRealPath("../resources/static/"+fileName);
//        String filePath = "C:\\Users\\user\\spring\\woot\\woot\\src\\main\\resources\\static\\images";

        File file = new File(filePath);
        files.transferTo(file);

        //아이템 등록
        Item item = new Item();
        item.setItem_name(itemForm.getItem_name());
        item.setPrice(itemForm.getPrice());
        item.setStockQuantity(itemForm.getStockQuantity());
        item.setDtype(itemForm.getDtype());
        item.setFilePath("/images/"+fileName);

        itemService.saveItem(item);

        log.info("file path : " + filePath);

        return "redirect:/";
    }

}
