package by.epam.kvirykashvili.javalabtask.domain.parameters;

import by.epam.kvirykashvili.javalabtask.domain.model.User;
import lombok.Builder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Builder
public class UserSearchParameters implements SearchParameters<User> {

    private Long id;
    private String login;
    private String password;

    @Override
    public Query setCriteria(Query query) {
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (login != null) {
            query.addCriteria(Criteria.where("login").is(login));
        }
        if (password != null) {
            query.addCriteria(Criteria.where("password").is(password));
        }
        return query;
    }
}
