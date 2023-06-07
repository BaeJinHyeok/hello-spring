package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest // 스프링 컨테이너와 테스트를 함께 심행한다.
@Transactional //트랜잭션이 롤백되기 때문에 DB에 반영안됨 -> 계속 테스트가능(다음테스트에 영향X) ->  DB연결까지 해보는 통합테스트
    //서비스에는 정상적이게 동작하고 테스트케이스시에는 롤백됨
class MemberServiceIntegrationTest {

     @Autowired MemberService memberService;
     //@Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given 뭔가가 주어졌을 때
        Member member = new Member();
        member.setName("hello");

        //when 이걸을 실행했을 때
        Long saveId = memberService.join(member);

        //then 결과가 이게 나와야함
        Member findMember = memberService.findOne((saveId)).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*try{
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then
    }
}