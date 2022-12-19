package com.example.Task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Getter
@EqualsAndHashCode
@Entity
@Table(name = "ROLE")
public class RoleEntity implements GrantedAuthority {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Size(max = 16)
    @Column(name = "NAME")
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
