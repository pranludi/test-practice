package io.pranludi.testpractice.member;

import jakarta.transaction.Transactional;
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

    public Member findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

}
