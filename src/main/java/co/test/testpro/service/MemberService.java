package co.test.testpro.service;

import co.test.testpro.domain.User;
import co.test.testpro.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {//디펜더시 인젝션
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    public Long join(User user){
        valdateDuplicateMember(user);//같은 이름이 있는 중복 회원 x

        memberRepository.save(user);
        return user.getUserId();
    }

    private void valdateDuplicateMember(User user) {
        memberRepository.findByUsername(user.getUsername())
            .ifPresent(m -> { //만약에 같이 존재한다면 오류 던짐
            throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<User> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<User> findOne(Long memberId){
        return memberRepository.findByUserId(memberId);
    }
}
