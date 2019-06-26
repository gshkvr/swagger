package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import by.epam.kvirykashvili.javalabtask.domain.parameters.SearchParameters;
import by.epam.kvirykashvili.javalabtask.repository.CountryRepository;
import by.epam.kvirykashvili.javalabtask.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final InitDbService initDbService;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, InitDbService initDbService, MongoTemplate mongoTemplate) {
        this.countryRepository = countryRepository;
        this.initDbService = initDbService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(Country country) {
        countryRepository.save(country);
    }

    @Override
    public List<Country> readAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(long id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        return optionalCountry.orElse(null);
    }

    @Override
    public List<Country> readList(SearchParameters parameters) {
        Query query = new Query();
        parameters.setCriteria(query);
        return mongoTemplate.find(query, Country.class);
    }

    @Override
    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    public void update(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void delete(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public List<Country> getPage(int rows, int page) {
        return countryRepository.findAll(PageRequest.of(page, rows)).getContent();
    }

    @PostConstruct
    public void initDb() {
        initDbService.initCountries();
    }
}
