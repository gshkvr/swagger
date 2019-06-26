package by.epam.kvirykashvili.javalabtask.rest;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestService<T> {
    T getById(long id);

    List<T> getAll();

    ResponseEntity<String> update(T entity);

    ResponseEntity<String> add(T entity);

    ResponseEntity<String> delete(long id);
}
