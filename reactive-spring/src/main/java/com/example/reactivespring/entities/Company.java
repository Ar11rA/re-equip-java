package com.example.reactivespring.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("company")
public class Company {
    @Id
    private Integer id;
    private String name;
}
