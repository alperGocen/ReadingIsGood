package com.rig.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AbstractDateEntity extends AbstractIdEntity {

    @Setter(AccessLevel.NONE)
    @Column(name = "CREATE_DATE")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    @CreationTimestamp
    private LocalDateTime updateDate;
}
