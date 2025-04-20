package io.pranludi.testpractice.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User find(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
