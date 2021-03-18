package woo.woot.domain;

import lombok.Getter;
import lombok.Setter;

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

}
