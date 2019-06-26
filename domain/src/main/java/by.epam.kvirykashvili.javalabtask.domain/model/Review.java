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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Review implements Serializable {

    private static final long serialVersionUID = 1277929585720264864L;

    @Transient
    public static final String SEQUENCE_NAME = "reviews_sequence";

    @Id
    private long id;

    private Date date;

    private String text;

    @DBRef
    private User user;

    @DBRef
    private Tour tour;

}