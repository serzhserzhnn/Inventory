package com.example.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CategoryDTO {
    @NotBlank
    private int id;
    @NotBlank
    private String name;
}
