package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Review;
import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.User;
import by.epam.kvirykashvili.javalabtask.domain.parameters.SearchParameters;
import by.epam.kvirykashvili.javalabtask.repository.ReviewRepository;
import by.epam.kvirykashvili.javalabtask.repository.TourRepository;
import by.epam.kvirykashvili.javalabtask.repository.UserRepository;
import by.epam.kvirykashvili.javalabtask.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final TourRepository tourRepository;

    private final InitDbService initDbService;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, TourRepository tourRepository, InitDbService initDbService, MongoTemplate mongoTemplate) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.initDbService = initDbService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(Review review) {
        reviewRepository.save(review);
        Optional<User> optionalUser = userRepository.findById(review.getUser().getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getReviews().add(review);
            userRepository.save(user);
        }
        Optional<Tour> optionalTour = tourRepository.findById(review.getTour().getId());
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            tour.getReviews().add(review);
            tourRepository.save(tour);
        }
    }

    @Override
    public List<Review> readAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        return optionalReview.orElse(null);
    }

    @Override
    public List<Review> readList(SearchParameters parameters) {
        Query query = new Query();
        parameters.setCriteria(query);
        return mongoTemplate.find(query, Review.class);
    }

    @Override
    public void update(Review review) {
        reviewRepository.save(review);
    }

    @Transactional
    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public List<Review> getPage(int rows, int page) {
        return reviewRepository.findAll(PageRequest.of(page, rows)).getContent();
    }

    @PostConstruct
    public void initDb() throws ParseException {
        initDbService.initReviews();
    }
}
