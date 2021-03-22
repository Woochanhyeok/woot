package woo.woot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woo.woot.domain.Dtype;
import woo.woot.domain.Item;
import woo.woot.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save (Item item) {
        if (item.getItem_id() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
    //상품 종류에 따라 가져오기
    public List<Item> findItemsByDtype(Dtype dtype) {
            return em.createQuery("select i from Item i where i.dtype = :dtype", Item.class)
                    .setParameter("dtype", dtype)
                    .getResultList();
    }

}
