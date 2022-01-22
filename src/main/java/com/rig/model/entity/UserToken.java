package com.rig.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "USER_TOKEN")
@Where(clause = "IS_DELETED = false")
public class UserToken extends AbstractIdEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
}
