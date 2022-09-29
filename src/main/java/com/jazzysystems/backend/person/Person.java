package com.jazzysystems.backend.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Person", indexes = @Index(name = "uniqueDni", columnList = "dni", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(name = "firstName", nullable = false, length = 30)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "dni", nullable = false, unique = true)
    private Long dni;

    @Column(name = "phone", nullable = false)
    private Long phone;

}
