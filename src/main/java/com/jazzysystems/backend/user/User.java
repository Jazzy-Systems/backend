package com.jazzysystems.backend.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", columnDefinition = "serial")
    private Long userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isEnabled", nullable = false)
    private boolean isEnabled;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

}
