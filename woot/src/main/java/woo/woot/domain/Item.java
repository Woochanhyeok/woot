package woo.woot.domain;

import lombok.Getter;
import lombok.Setter;
import woo.woot.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    private Long item_id;

    private String item_name;

    @Enumerated(EnumType.STRING)
    private Dtype dtype;

    private int price;

    private int stockQuantity;

    private int order_count;

    private String filePath;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    //== 비즈니스 로직 == (재고 조정)
    //재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //재고 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stockquantity");
        }
        this.stockQuantity = restStock;
    }
}
