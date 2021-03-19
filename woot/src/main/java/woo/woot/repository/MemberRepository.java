package woo.woot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woo.woot.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save (Member member) {
        em.persist(member);
    }

    public Member findOne (Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll () {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    //username으로 멤버 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.username = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
