package com.jazzysystems.backend.resident;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.jazzysystems.backend.apartment.Apartment;
import com.jazzysystems.backend.person.Person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Resident", indexes = @Index(name = "uniquePerson", columnList = "person_person_id", unique = true))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Resident implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long residentId;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Apartment apartment;

    @Column(name = "isRepresentative")
    private Boolean isRepresentative;

    @Column(name = "isResident")
    private Boolean isResident;

}
