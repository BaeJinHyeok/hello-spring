package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class MemoryMemberRepository implements MemberRepository {
    private  static Map<Long, Member> store = new HashMap<>();
    private  static  long squence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++squence);
        store.put(member.getId(), member); //MAP에 저장됨
        return member;
    }

    @Override
    public Optional<Member> findBtyId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals((name)))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public  void clearStore(){
        store.clear();
    }
}
