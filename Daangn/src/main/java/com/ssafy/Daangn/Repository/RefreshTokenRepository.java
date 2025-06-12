package com.ssafy.Daangn.Repository;

import com.ssafy.Daangn.Domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    void deleteByToken(String token);

    void deleteByExpireDateBefore(LocalDate now);
}
