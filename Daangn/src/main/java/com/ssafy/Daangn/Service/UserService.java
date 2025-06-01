package com.ssafy.Daangn.Service;

import com.ssafy.Daangn.Domain.User;
import com.ssafy.Daangn.Exception.UserNotFoundException;
import com.ssafy.Daangn.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 전체 사용자 조회
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 ID로 사용자 조회
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "사용자를 찾을 수 없습니다: " + userId));
    }

    // 닉네임으로 사용자 검색
    public User findUsersByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    // 위치별 사용자 조회
    public List<User> findUsersByLocationId(Integer locationId) {
        return userRepository.findByLocationLocationId(locationId);
    }

    // 사용자 저장 (회원가입)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // 사용자 정보 수정
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // 사용자 삭제
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}