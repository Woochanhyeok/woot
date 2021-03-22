package woo.woot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woo.woot.domain.Member;
import woo.woot.domain.Review;
import woo.woot.service.ItemService;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;
    private final ItemRepository itemRepository;

    public void save(Review review) {
        em.persist(review);
    }

    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    //해당 아이템의 리뷰만 select
    public List<Review> findEachReview(Long id) {
        return em.createQuery("select r from Review r where r.item = :id",Review.class)
                .setParameter("id", itemRepository.findOne(id))
                .getResultList();
    }

}