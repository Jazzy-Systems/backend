package com.jazzysystems.backend.pack;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.securityguard.SecurityGuard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Pack")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packId;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SecurityGuard securityGuard;

    @Column(name = "messengerName")
    private String messengerName;

    @Column(name = "typePack", nullable = false)
    private String typePack;

    @Column(name = "observation")
    private String observation;

    @Column(name = "received", nullable = false)
    private Boolean received;

    @Column(name = "datePickedUp")
    private LocalDateTime datePickedUp;

    @Column(name = "dateArrival", nullable = false)
    private LocalDateTime dateArrival;
}
