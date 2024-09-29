package com.antoniosousa.ecommerce.domain.repositories;

import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.integrated = :integrated WHERE u.id = :id")
    void updateIntegratedById(@Param("id") Long id, @Param("integrated") boolean integrated);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accountStatus = :accountStatus WHERE u.email = :email")
    void updateAccountStatus(@Param("email") String email, @Param("accountStatus") AccountStatus accountStatus);

    @Transactional(readOnly = true)
    List<User> findByIntegratedFalse();

}
