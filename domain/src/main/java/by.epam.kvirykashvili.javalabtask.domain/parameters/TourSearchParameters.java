package by.epam.kvirykashvili.javalabtask.domain.parameters;

import by.epam.kvirykashvili.javalabtask.domain.model.*;
import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

@Builder
public class TourSearchParameters implements SearchParameters<Tour> {

    private Long id;
    private String photo;
    private Date date;
    private Integer duration;
    private String description;
    private Integer cost;
    private TourType tourType;
    private Long hotelId;
    private Long countryId;
    private Long userId;

    @Override
    public Query setCriteria(Query query) {
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (photo != null) {
            query.addCriteria(Criteria.where("photo").is(photo));
        }
        if (date != null) {
            query.addCriteria(Criteria.where("date").is(date));
        }
        if (duration != null) {
            query.addCriteria(Criteria.where("duration").is(duration));
        }
        if (description != null) {
            query.addCriteria(Criteria.where("description").is(description));
        }
        if (cost != null) {
            query.addCriteria(Criteria.where("cost").is(cost));
        }
        if (tourType != null) {
            query.addCriteria(Criteria.where("tourType").is(tourType));
        }
        if (hotelId != null) {
            query.addCriteria(Criteria.where("hotel").is(Hotel.builder().id(hotelId).build()));
        }
        if (countryId != null) {
            query.addCriteria(Criteria.where("country").is(Country.builder().id(countryId).build()));
        }
        if (userId != null) {
            query.addCriteria(Criteria.where("user").is(User.builder().id(userId).build()));
        }
        return query;
    }
}
