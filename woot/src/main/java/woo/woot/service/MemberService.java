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

    public String findNameByUsername(String username) {
        Member member = memberRepository.findByName(username).get(0);
        return member.getName();
    }

    public Member findByUsername(String username) {
        return memberRepository.findByName(username).get(0);
    }

    //로그인 체크
    public boolean signInCheck(String username, String password) {
        List<Member> members = memberRepository.findByName(username);
        for (Member member : members) {
            if(member.getUsername().equals(username)) {
                if(member.getPassword().equals(password))
                    return true;                    //로그인 성공
            }
        }
        return false;                               //로그인 실패
    }

}
