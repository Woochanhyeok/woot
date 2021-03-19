package woo.woot.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.Member;
import woo.woot.domain.Order;
import woo.woot.domain.SexType;
import woo.woot.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("우찬혁");
        member.setUsername("wch7904");
        member.setSex(SexType.MAN);

        Long saveId = memberService.join(member);
        Assert.assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test
    public void 회원_주문수_정렬() throws Exception {
        Member member1 = new Member();
        member1.setName("우찬혁");
        member1.setUsername("wch7904");
        member1.setSex(SexType.MAN);

        Order order1 = new Order();
        Order order2 = new Order();


        Member member2 = new Member();
        member2.setName("김철수");
        member2.setUsername("w123123");
        member2.setSex(SexType.MAN);

        Long saveId1 = memberService.join(member1);
        Long saveId2 = memberService.join(member2);

        List<Member> members = memberRepository.findAll();

    }


    @Test(expected = IllegalStateException.class)
    public void 중복회원_예외처리() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setUsername("wch");
        member2.setUsername("wch");

        memberService.join(member1);
        memberService.join(member2);
    }

}