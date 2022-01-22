package com.rig.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "AUTHORITY")
public class Authority extends AbstractIdEntity {

    @Column(name = "name")
    private String name;
}
