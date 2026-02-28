package com.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "oauth_scope")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthScope {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(length = 100)
    private String id;

    @Column(name = "scope_name", unique = true, nullable = false)
    private String scopeName;

    private String description;
}