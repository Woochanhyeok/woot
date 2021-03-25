package woo.woot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.Item;
import woo.woot.domain.Member;
import woo.woot.domain.Order;
import woo.woot.domain.OrderItem;
import woo.woot.repository.ItemRepository;
import woo.woot.repository.MemberRepository;
import woo.woot.repository.OrderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //해당 아이템 order_count 증가
        item.setOrder_count(item.getOrder_count()+count);
        itemRepository.save(item);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getOrder_id();
    }

    //주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    //검색
}
