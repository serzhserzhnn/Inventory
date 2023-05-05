package com.example.inventory.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "things")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Things {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid")
    @Type(type = "uuid-char")
    private UUID id;
    private String name;
    private String description;
    private String location;
    private Integer category;
    private Integer quantity;
    @Column(name = "date_end", columnDefinition = "DATE")
    private Date dateEnd;
    @Column(name = "user_id")
    private String userId;

    public Things(String name, String description, String location, Integer category,
                  Integer quantity, Date dateEnd, String userId) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.quantity = quantity;
        this.dateEnd = dateEnd;
        this.userId = userId;
    }
}
