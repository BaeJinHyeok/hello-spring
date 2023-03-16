package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
@Configuration
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) { // 외부에서 memeberRepository를 넣어줌 ->
                                                              // Dependency injection = DI 라고 함
        this.memberRepository = memberRepository;
    }

    /*
    회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        //result.get() or orELseGet -> 같이 값을 꺼낼 순 있지만 권하진 않음.
               .ifPresent(m -> {

                    throw  new IllegalStateException("이미 존재하는 회원입니다.");
               });
    }
    /*
       전체회원조회
        */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findBtyId(memberId);
    }
}
