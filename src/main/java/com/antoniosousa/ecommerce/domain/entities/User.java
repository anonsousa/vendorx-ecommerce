package com.antoniosousa.ecommerce.domain.entities;

import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false, unique = true)
    private String username;

    @Column(length = 120, nullable = false, unique = true)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Column(length = 30)
    private String phone;

    @Column(length = 30, name = "cpf_cnpj")
    private String CpfCnpj;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);

    private LocalDateTime lastLogin;

}
