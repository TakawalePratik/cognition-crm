package com.cognition.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false, unique = true, length = 50)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}