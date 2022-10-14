package com.jazzysystems.backend.communique;

import java.time.LocalDate;

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
import com.jazzysystems.backend.typeCommunique.TypeCommunique;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Communique", indexes = {})
public class Communique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communiqueId;

    @Column(name = "titleCommunique", nullable = false)
    private String titleCommunique;

    @Column(name = "datePublished", nullable = false)
    private LocalDate datePublished;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TypeCommunique typeCommunique;

}
