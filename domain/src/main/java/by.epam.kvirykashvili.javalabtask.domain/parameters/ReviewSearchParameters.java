package by.epam.kvirykashvili.javalabtask.domain.parameters;

import by.epam.kvirykashvili.javalabtask.domain.model.Review;
import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.User;
import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

@Builder
public class ReviewSearchParameters implements SearchParameters<Review> {

    private Long id;
    private Date date;
    private String text;
    private Long userId;
    private Long tourId;

    @Override
    public Query setCriteria(Query query) {
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (date != null) {
            query.addCriteria(Criteria.where("date").is(date));
        }
        if (text != null) {
            query.addCriteria(Criteria.where("text").is(text));
        }
        if (userId != null) {
            query.addCriteria(Criteria.where("user").is(User.builder().id(userId).build()));
        }
        if (tourId != null) {
            query.addCriteria(Criteria.where("tour").is(Tour.builder().id(tourId).build()));
        }
        return query;
    }
}
