package com.antoniosousa.ecommerce.domain.repositories;

import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.user.id IN :userIds")
    List<VerificationToken> findAllByUserIds(@Param("userIds") List<Long> userIds);

    @Transactional(readOnly = true)
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.token = :token")
    Optional<VerificationToken> findByToken(@Param("token") String token);
}

