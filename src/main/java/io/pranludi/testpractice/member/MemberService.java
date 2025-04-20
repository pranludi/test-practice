package io.pranludi.testpractice.member;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Member save(Member member) {
        return repository.save(member);
    }

    public Member find(String id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Member> findAll() {
        return repository.findAll();
    }

    public Member findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

}
