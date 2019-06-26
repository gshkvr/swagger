package by.epam.kvirykashvili.javalabtask.domain.parameters;

import org.springframework.data.mongodb.core.query.Query;

public interface SearchParameters<T> {
    Query setCriteria(Query query);
}
