package com.jazzysystems.backend.role;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Column(name = "isEnabled", nullable = false)
    private boolean isEnabled;

}
