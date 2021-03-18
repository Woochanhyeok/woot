package woo.woot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {

    @Id @GeneratedValue
    private Long review_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String content;

    private LocalDateTime reviewDate;

    //연관관계 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getReviews().add(this);
    }
}
