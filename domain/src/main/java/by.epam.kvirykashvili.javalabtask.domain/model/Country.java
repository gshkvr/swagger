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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Country implements Serializable {

    private static final long serialVersionUID = -2848428274780656759L;

    @Transient
    public static final String SEQUENCE_NAME = "countries_sequence";

    @Id
    private long id;

    private String name;

    @DBRef(lazy = true)
    private Set<Tour> tours = new HashSet<>();
}