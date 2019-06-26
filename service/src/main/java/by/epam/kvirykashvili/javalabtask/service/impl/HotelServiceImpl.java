package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import by.epam.kvirykashvili.javalabtask.domain.parameters.SearchParameters;
import by.epam.kvirykashvili.javalabtask.repository.HotelRepository;
import by.epam.kvirykashvili.javalabtask.service.HotelService;
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
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final InitDbService initDbService;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, InitDbService initDbService, MongoTemplate mongoTemplate) {
        this.hotelRepository = hotelRepository;
        this.initDbService = initDbService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> readAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElse(null);
    }

    @Override
    public List<Hotel> readList(SearchParameters parameters) {
        Query query = new Query();
        parameters.setCriteria(query);
        return mongoTemplate.find(query, Hotel.class);
    }

    @Override
    public void update(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void delete(Hotel hotel) {
        hotelRepository.delete(hotel);
    }

    @Override
    public List<Hotel> getPage(int rows, int page) {
        return hotelRepository.findAll(PageRequest.of(page, rows)).getContent();
    }

    @PostConstruct
    public void initDb() {
        initDbService.initHotels();
    }
}
