package woo.woot.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.*;
import woo.woot.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    @Rollback(value = false)
    public void 상품_주문() throws Exception {
        Member member = new Member();
        member.setUsername("wch7904");
        member.setName("우찬혁");
        member.setPassword("w123123");
        member.setSex(SexType.MAN);
        em.persist(member);

        Item item = new Item();
        item.setItem_name("IAB 후드");
        item.setPrice(60000);
        item.setDtype(Dtype.TOP);
        item.setStockQuantity(100);
        item.setFilePath("/user/asd");
        em.persist(item);

        Long orderId = orderService.order(member.getMember_id(), item.getItem_id(), 3);

        Order getOrder = orderRepository.findOne(orderId);


        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals("주문한 상품 종류 수가 같아야 함",1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량", 60000 * 3, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고 감소", 97, item.getStockQuantity());
        assertEquals("멤버의 orders의 길이가 1이어야 함",1,member.getOrders().size());

    }

    @Test
    public void 주문_취소() throws Exception {

    }
}