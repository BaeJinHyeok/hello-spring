package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

//단위테스트. 좋은테스트일 확률이 높음. 순수 자바코드로 테스트를 짜는 연습이 필요함.

class MemberServiceTest {

     MemberService memberService;
     MemoryMemberRepository memberRepository;

     @BeforeEach
     public void beforeEach(){
         memberRepository = new MemoryMemberRepository();
         memberService = new MemberService(memberRepository);
     }

    @AfterEach //메서드 실행끝날때마다 동작 콜백메서드
    public void afterEach(){
        memberRepository.clearStore(); //순서상관없이 메서드 후에 클리어 필요(테스트는 서로 순서와 의존관계없이 설계되어야함 하나 끝날때마다
        //저장소나 공용데이터들을 깔끔히 지워줘야 문제가 없음
    }
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
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}