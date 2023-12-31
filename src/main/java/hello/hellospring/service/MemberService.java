package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional //jpa를 쓰려면 JPA가 있어야 함
public class MemberService {
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *   회원가입
     * 
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        //findByName을 하면 옵서녈 객체가 리턴됨
        //옵셔널을 쓰게 되면 옵셔널 객체로 한번 감싸지기 때문에 이 안에 있는 여러 메서드를 사용할 수 있음
        //만약 result에 값이 존재한다면 다음이 동작이 되는데, m은 그냥 변수명을 이렇게 정한 것임
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }


    /**
     *
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
