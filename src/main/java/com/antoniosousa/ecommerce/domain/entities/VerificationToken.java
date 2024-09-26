package com.antoniosousa.ecommerce.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER_VERIFICATION")
@Data
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    private boolean consumed = Boolean.FALSE;

    public VerificationToken(User user,
                             String name,
                             String email,
                             String token) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusDays(2);
    }
}
