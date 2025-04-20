package io.pranludi.testpractice.member;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.find(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
