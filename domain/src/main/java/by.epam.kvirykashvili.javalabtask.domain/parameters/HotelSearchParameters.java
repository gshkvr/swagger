package by.epam.kvirykashvili.javalabtask.domain.parameters;

import by.epam.kvirykashvili.javalabtask.domain.model.Features;
import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Builder
public class HotelSearchParameters implements SearchParameters<Hotel> {

    private Long id;
    private String name;
    private Integer stars;
    private String website;
    private String latitude;
    private String longitude;
    private Features[] features;

    @Override
    public Query setCriteria(Query query) {
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        if (stars != null) {
            query.addCriteria(Criteria.where("stars").is(stars));
        }
        if (website != null) {
            query.addCriteria(Criteria.where("website").is(website));
        }
        if (latitude != null) {
            query.addCriteria(Criteria.where("latitude").is(latitude));
        }
        if (longitude != null) {
            query.addCriteria(Criteria.where("longitude").is(longitude));
        }
        if (features != null) {
            query.addCriteria(Criteria.where("features").is(features));
        }
        return query;
    }
}
