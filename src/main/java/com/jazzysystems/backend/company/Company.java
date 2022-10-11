package com.jazzysystems.backend.company;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Company", indexes = @Index(name = "uniqueCompanyName", columnList = "companyName", unique = true))
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(name = "nit", nullable = false, unique = true)
    private Long nit;

    @Column(name = "companyName", nullable = false, unique = true)
    private String companyName;

    @Column(name = "phone", nullable = false)
    private long phone;

    @Column(name = "companyEmail", nullable = false, unique = true)
    private String companyEmail;

     @Column(name = "codeCompany", nullable = false, unique = true)
    private String codeCompany;

}
