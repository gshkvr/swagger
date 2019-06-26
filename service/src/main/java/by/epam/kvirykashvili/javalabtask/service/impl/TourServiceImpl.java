package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.parameters.SearchParameters;
import by.epam.kvirykashvili.javalabtask.repository.CountryRepository;
import by.epam.kvirykashvili.javalabtask.repository.HotelRepository;
import by.epam.kvirykashvili.javalabtask.repository.TourRepository;
import by.epam.kvirykashvili.javalabtask.service.ImportService;
import by.epam.kvirykashvili.javalabtask.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourServiceImpl implements TourService {

    private final ImportService importService;

    private final TourRepository tourRepository;

    private final CountryRepository countryRepository;

    private final HotelRepository hotelRepository;

    private final MongoTemplate mongoTemplate;

    private final InitDbService initDbService;

    @Autowired
    public TourServiceImpl(ImportService importService, TourRepository tourRepository, CountryRepository countryRepository, HotelRepository hotelRepository, MongoTemplate mongoTemplate, InitDbService initDbService) {
        this.importService = importService;
        this.tourRepository = tourRepository;
        this.countryRepository = countryRepository;
        this.hotelRepository = hotelRepository;
        this.mongoTemplate = mongoTemplate;
        this.initDbService = initDbService;
    }

    @Override
    public void create(Tour tour) {
        tourRepository.save(tour);
        Optional<Country> optionalCountry = countryRepository.findById(tour.getCountry().getId());
        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();
            country.getTours().add(tour);
            countryRepository.save(country);
        }
        Optional<Hotel> optionalHotel = hotelRepository.findById(tour.getHotel().getId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.getTours().add(tour);
            hotelRepository.save(hotel);
        }
    }

    @Override
    public List<Tour> readAll() {
        return tourRepository.findAll();
    }

    @Override
    public Tour findById(long id) {
        Optional<Tour> optionalTour = tourRepository.findById(id);
        return optionalTour.orElse(null);
    }

    @Override
    public List<Tour> readList(SearchParameters parameters) {
        Query query = new Query();
        parameters.setCriteria(query);
        return mongoTemplate.find(query, Tour.class);
    }

    @Override
    public void update(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public void delete(Tour tour) {
        tourRepository.delete(tour);
    }

    @Override
    public List<Tour> getPage(int rows, int page) {
        return tourRepository.findAll(PageRequest.of(page, rows)).getContent();
    }

    @Override
    public void importToursFromCsv(String csvFile) {
        importService.importToursFromCsv(csvFile);
    }

    @PostConstruct
    public void createTours() throws ParseException {
        initDbService.initTours();
    }
}
