package com.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Entity
@Table(name = "oauth_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthClient {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(length = 100)
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "oauth_client_scopes",
            joinColumns = @JoinColumn(name = "oauth_client_id"),
            inverseJoinColumns = @JoinColumn(name = "scopes_id")
    )
    private Set<OAuthScope> scopes;
}