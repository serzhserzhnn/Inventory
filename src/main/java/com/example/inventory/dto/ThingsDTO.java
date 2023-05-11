package com.example.inventory.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ThingsDTO {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String location;
    @NotBlank
    private Integer category;
    @NotBlank
    private Integer quantity;
    @NotBlank
    private Date dateEnd;
    @NotBlank
    private String userId;
}
