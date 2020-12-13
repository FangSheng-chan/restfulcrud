package com.ss.restfulcrud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Department implements Serializable {
    private Integer departmentId;
    private String departmentName;
}
