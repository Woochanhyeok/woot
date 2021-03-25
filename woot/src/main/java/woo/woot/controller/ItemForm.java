package woo.woot.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import woo.woot.domain.Dtype;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemForm {

    @NotEmpty(message = "상품 이름 입력은 필수입니다.")
    private String item_name;
    @NotNull(message = "상품 가격 입력은 필수입니다.")
    private int price;
    @NotNull(message = "상품 재고 수량 입력은 필수입니다.")
    private int stockQuantity;

    @NotNull(message = "상품 종류 입력은 필수입니다.")
    private Dtype dtype;

    @NotNull(message = "상품 이미지 등록은 필수입니다.")
    private MultipartFile file;

}
