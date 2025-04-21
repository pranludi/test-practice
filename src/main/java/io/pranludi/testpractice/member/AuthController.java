package io.pranludi.testpractice.member;

import io.pranludi.testpractice.config.security.common.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final JwtUtil jwtUtil;
    final MemberService service;

    public AuthController(JwtUtil jwtUtil, MemberService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody String id) {
        String token = jwtUtil.generateToken(id);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        if (jwtUtil.isTokenValid(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateToken(username);
            return ResponseEntity.ok(newAccessToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/add")
    public ResponseEntity<Member> save(@RequestBody Member member) {
        Member saved = service.save(member);
        return ResponseEntity.ok(saved);
    }

}