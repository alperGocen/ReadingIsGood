package com.rig.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "USER_ROLE")
public class UserRole extends AbstractIdEntity {

    @Column(name = "NAME")
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE_AUTHORITY",
            joinColumns = @JoinColumn(name = "USER_ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID"))
    private Set<Authority> authorities;
}
