package woo.woot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    private Long order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //연관관계 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    //생성 메소드
    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        //배송 준비
        order.setDeliveryStatus(DeliveryStatus.READY);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직 ==
    //주문 취소
    public void cancel() {
        //배송중, 배송 완료인 경우 취소 불가능
        if(deliveryStatus == DeliveryStatus.START)
            throw new IllegalStateException("이미 배송을 시작한 상품은 취소가 불가능합니다.");
        if(deliveryStatus == DeliveryStatus.FINISH)
            throw new IllegalStateException("이미 배송중인 상품은 취소가 불가능합니다.");

        this.setOrderStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==
    //전체 주문 가격
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem :
                orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
