package com.example.inventory.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "things")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Things {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String location;
    private Integer category;
    private Integer quantity;
    @Column(name = "date_start")
    private String dateStart;
    @Column(name = "date_end")
    private String dateEnd;

    public Things(String name, String description, Integer category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
