package com.encore.bbabap.service.user;

import com.encore.bbabap.api.user.request.SignUpUserRequest;
import com.encore.bbabap.api.user.request.UserUpdateRequest;
import com.encore.bbabap.api.user.response.UserResponse;
import com.encore.bbabap.domain.user.User;
import com.encore.bbabap.exception.user.UserEmailDuplicateException;
import com.encore.bbabap.exception.user.UserNotFoundException;
import com.encore.bbabap.repository.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserResponse signUp(SignUpUserRequest request) {
        checkExistUserEmail(request);

        User user = userCreate(request);

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .email(savedUser.getEmail())
                .nickname(savedUser.getNickname())
                .carType(savedUser.getCarType())
                .build();
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findUsersByDeletedYnFalse();

        return users.stream()
                .map(user -> new UserResponse(user.getEmail(), user.getNickname(),
                        user.getCarType()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMemberById(Long id) {
        User user = validateUser(id);
        user.deleteUser();
    }

    @Transactional
    public void updateMember(Long id, UserUpdateRequest request) {
        User user = validateUser(id);
        user.updateMemberDetail(request);
    }

    private void checkExistUserEmail(SignUpUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailDuplicateException("이미 사용중인 이메일 입니다.");
        }
    }

    private User validateUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("찾는 회원이 없습니다."));
    }

    private User userCreate(SignUpUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .carType(request.getCarType())
                .build();
    }

//    @Transactional
//    public UserResponse login(LoginUserRequest request) {
//        User findUser = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
//
//        if (findUser.getPassword().equals(request.getPassword())) {
//            System.out.println("로그인 하였습니다.");
//        }
//
//        System.out.println("비밀번호 또는 아이디를 확인해주세요");
//
//        return null;
//    }
}
