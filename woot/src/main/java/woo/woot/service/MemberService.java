package woo.woot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.Member;
import woo.woot.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join (Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMember_id();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getUsername());
        if(!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 전체 조회 (주문수로 내림차순 정렬)


}