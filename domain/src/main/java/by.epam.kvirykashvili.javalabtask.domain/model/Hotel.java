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
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Hotel implements Serializable {

    private static final long serialVersionUID = -3448788786752231291L;

    @Transient
    public static final String SEQUENCE_NAME = "hotels_sequence";

    @Id
    private long id;

    private String name;

    private int stars;

    private String website;

    private String latitude;

    private String longitude;

    private Features[] features;

    @DBRef(lazy = true)
    private Set<Tour> tours = new HashSet<>();

}