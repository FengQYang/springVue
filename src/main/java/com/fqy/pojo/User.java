package com.fqy.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class User {

    @Id
    private Integer id;
    @Column(name = "username")
    private String userName;
    @Column(name = "address")
    private String address;
}
