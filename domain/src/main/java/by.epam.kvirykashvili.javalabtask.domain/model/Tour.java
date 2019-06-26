package by.epam.kvirykashvili.javalabtask.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Tour implements Serializable {

    private static final long serialVersionUID = 2315670959539664302L;

    @Transient
    public static final String SEQUENCE_NAME = "tours_sequence";

    @Id
    private long id;

    private String photo;

    private Date date;

    private int duration;

    private String description;

    private int cost;

    private TourType tourType;

    @DBRef
    private Hotel hotel;

    @DBRef
    private Country country;

    @DBRef(lazy = true)
    private Set<User> users = new HashSet<>();

    @DBRef(lazy = true)
    private Set<Review> reviews = new HashSet<>();
}