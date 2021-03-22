package woo.woot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired MemberService memberService;
    @Autowired ReviewService reviewService;
    @Autowired ItemService itemService;

    @Test
    public void 리뷰_등록() {
        //회원 등록
        Member member = new Member();
        member.setName("우찬혁");
        member.setUsername("wch7904");
        member.setSex(SexType.MAN);

        Long saveId = memberService.join(member);

        //아이템 등록
        Item item1 = new Item();
        item1.setItem_name("나이키 모자");
        item1.setDtype(Dtype.HEADWEAR);
        item1.setFilePath("/asdasd/asdasd");
        item1.setPrice(10000);
        item1.setStockQuantity(100);
        item1.setOrder_count(0);

        itemService.saveItem(item1);

        //리뷰 등록
        Review review = new Review();
        review.setReviewDate(LocalDateTime.now());
        review.setMember(memberService.findOne(member.getMember_id()));
        review.setContent("모자가 엄청 예쁘네요! 맘에 들어요~");
        review.setItem(itemService.findOne(item1.getItem_id()));

        reviewService.registerReview(review);

    }

    @Test
    public void 특정_회원_리뷰_조회() {
//회원 등록
        Member member = new Member();
        member.setName("우찬혁");
        member.setUsername("wch7904");
        member.setSex(SexType.MAN);

        Long saveId = memberService.join(member);

        //아이템 등록
        Item item1 = new Item();
        item1.setItem_name("나이키 모자");
        item1.setDtype(Dtype.HEADWEAR);
        item1.setFilePath("/asdasd/asdasd");
        item1.setPrice(10000);
        item1.setStockQuantity(100);
        item1.setOrder_count(0);

        itemService.saveItem(item1);

        //리뷰 2개 등록
        Review review = new Review();
        review.setReviewDate(LocalDateTime.now());
        review.setMember(memberService.findOne(member.getMember_id()));
        review.setContent("모자가 엄청 예쁘네요! 맘에 들어요~");
        review.setItem(itemService.findOne(item1.getItem_id()));

        reviewService.registerReview(review);

        Review review2 = new Review();
        review2.setReviewDate(LocalDateTime.now());
        review2.setMember(memberService.findOne(member.getMember_id()));
        review2.setContent("모자 재질이 좋네요");
        review2.setItem(itemService.findOne(item1.getItem_id()));

        reviewService.registerReview(review2);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review2);

        assertEquals(reviews, reviewService.findEachReview(item1.getItem_id()));
    }
}