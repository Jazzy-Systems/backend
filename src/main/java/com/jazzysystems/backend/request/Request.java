package com.jazzysystems.backend.request;


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
import com.jazzysystems.backend.typeRequest.TypeRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Request")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TypeRequest typeRequest;

    @Column(name = "titleRequest", nullable = false)
    private String titleRequest;

    @Column(name = "descriptionRequest",  nullable = false)
    private String descriptionRequest;

    @Column(name = "responseRequest")
    private String responseRequest;

    @Column(name = "dateRequest",  nullable = false)
    private LocalDateTime dateRequest;

    @Column(name = "dateResponse")
    private LocalDateTime dateResponse;

    @Column(name = "statusRequest")
    private Boolean statusRequest;

}
