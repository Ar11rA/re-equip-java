package com.example.reactivespring.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CompanyExternalDTO {
    public String id;
    public String name;
    public String description;
    public List<String> tags;
    public double time;
    public LocalDateTime start;
    public LocalDateTime end;
}
