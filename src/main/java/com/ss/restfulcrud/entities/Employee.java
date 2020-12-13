package com.ss.restfulcrud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String gender;
    private Date birth;
    private String performance;
    private Integer DId;
    private Department department;

}
