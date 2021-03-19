package woo.woot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long member_id;

    private String username;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private SexType sex;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();


}
