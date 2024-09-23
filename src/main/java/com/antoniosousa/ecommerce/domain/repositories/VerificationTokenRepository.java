package com.antoniosousa.ecommerce.domain.repositories;

import com.antoniosousa.ecommerce.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Transactional(readOnly = true)
    List<VerificationToken> findAllByUserIds(List<Long> userIds);
}
