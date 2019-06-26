package by.epam.kvirykashvili.javalabtask.domain.parameters;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Builder
public class CountrySearchParameters implements SearchParameters<Country> {

    private Long id;
    private String name;

    @Override
    public Query setCriteria(Query query) {
        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        return query;
    }
}
