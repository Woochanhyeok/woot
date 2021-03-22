package woo.woot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.Review;
import woo.woot.repository.ItemRepository;
import woo.woot.repository.MemberRepository;
import woo.woot.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    //리뷰 등록
    public Long registerReview(Review review) {
        reviewRepository.save(review);
        return review.getReview_id();
    }

    //특정 아이템 리뷰 조회
    public List<Review> findEachReview(Long id) {
        return reviewRepository.findEachReview(id);
    }
}
