package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
/*
만약 테스트를 먼저 만들게 될 때 테스트를 먼저 만들고 구현클래스를 만드는걸 테스트주도개발(TDD) 라고함
* */

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //메서드 실행끝날때마다 동작 콜백메서드
    public void afterEach(){
        repository.clearStore(); //순서상관없이 메서드 후에 클리어 필요(테스트는 서로 순서와 의존관계없이 설계되어야함 하나 끝날때마다
                                 //저장소나 공용데이터들을 깔끔히 지워줘야 문제가 없음
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findBtyId(member.getId()).get();

        assertThat(member).isEqualTo(result);

    }
    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1); // member1 이랑 member2랑 같은지 확인

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
