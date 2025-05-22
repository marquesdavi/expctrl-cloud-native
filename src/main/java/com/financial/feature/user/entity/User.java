package com.financial.feature.user.entity;

import com.financial.feature.account.entity.Account;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name",  nullable = false)
    public String name;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    public Instant createdAt;
    
    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Account> accounts;
}
