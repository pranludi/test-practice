package io.pranludi.testpractice.user;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

// tdd 연습 중
public class UserServiceTest {

    private UserService userService;
    private UserPort userPort;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userPort = new UserAdapter(userRepository);
        userService = new UserService(userPort);
    }

    @Test
    void 회원가입() {
        final SignUpUserRequest req = 회원가입요청_생성();
        userService.SignUpUser(req);
    }

    private static SignUpUserRequest 회원가입요청_생성() {
        return new SignUpUserRequest(
            "테스트", 1, "password", UserType.STUDENT
        );
    }

    private record SignUpUserRequest(String name, Integer userId, String password, UserType userType) {

        SignUpUserRequest {
            Assert.hasText(name, "이름은 필수입니다.");
            Assert.notNull(userId, "id는 필수입니다.");
            Assert.hasText(password, "비밀번호는 필수입니다.");
            Assert.notNull(userType, "사용자 유형은 필수입니다.");
        }
    }

    public enum UserType {
        STUDENT,
        PROFESSOR;
    }

    public class UserService {

        final UserPort userPort;

        public UserService(UserPort userPort) {
            this.userPort = userPort;
        }

        public void SignUpUser(SignUpUserRequest req) {
            final User user = new User(req.name(), req.userId(), req.password(), req.userType());
            userPort.save(user);
        }
    }

    public class UserRepository {

        private Map<Long, User> persistence = new HashMap<>();
        private Long sequence = 0L;

        public void save(final User user) {
            user.assignId(++sequence);
            persistence.put(user.getId(), user);
        }
    }

    public interface UserPort {

        void save(final User user);
    }

    public class UserAdapter implements UserPort {

        final UserRepository userRepository;

        UserAdapter(final UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public void save(final User user) {
            userRepository.save(user);
        }
    }

    public class User {

        private Long id;
        private final String name;
        private final Integer userId;
        private final String password;
        private final UserType userType;

        public User(final String name, final Integer userId, final String password, final UserType userType) {
            Assert.hasText(name, "이름은 필수입니다.");
            Assert.notNull(userId, "id는 필수입니다.");
            Assert.hasText(password, "비밀번호는 필수입니다.");
            Assert.notNull(userType, "사용자 유형은 필수입니다.");

            this.name = name;
            this.userId = userId;
            this.password = password;
            this.userType = userType;
        }

        public void assignId(final Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
