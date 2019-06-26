package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.User;
import by.epam.kvirykashvili.javalabtask.domain.parameters.SearchParameters;
import by.epam.kvirykashvili.javalabtask.repository.TourRepository;
import by.epam.kvirykashvili.javalabtask.repository.UserRepository;
import by.epam.kvirykashvili.javalabtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final TourRepository tourRepository;

    private final MongoTemplate mongoTemplate;

    private final InitDbService initDbService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TourRepository tourRepository, MongoTemplate mongoTemplate, InitDbService initDbService) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.mongoTemplate = mongoTemplate;
        this.initDbService = initDbService;
    }

    @Override
    public void create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> readList(SearchParameters parameters) {
        Query query = new Query();
        parameters.setCriteria(query);
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with login: '" + login + "' wasn't found");
        }
    }

    @Override
    public void addFavouriteTour(User user, Tour tourToAdd) {
        Set<Tour> tours = user.getTours();
        if (tours == null) {
            tours = new HashSet<>();
        }
        tours.add(tourToAdd);
        user.setTours(tours);
        userRepository.save(user);
//        Set<User> users = tourToAdd.getUsers();
//        if (users == null) {
//            users = new HashSet<>();
//        }
//        users.add(user);
//        tourToAdd.setUsers(users);
//        tourRepository.save(tourToAdd);
    }

    @Override
    public void removeFavouriteTour(User user, Tour tourToRemove) {
        Set<Tour> tours = user.getTours();
        for (Tour t : tours) {
            if (t.getId() == tourToRemove.getId()) {
                tours.remove(t);
                break;
            }
        }
        user.setTours(tours);
        userRepository.save(user);
//        Set<User> users = tourToRemove.getUsers();
//        for (User u : users) {
//            if (user.getId() == u.getId()) {
//                users.remove(u);
//                break;
//            }
//        }
//        tourToRemove.setUsers(users);
//        tourRepository.save(tourToRemove);
    }

    @Override
    public List<User> getPage(int rows, int page) {
        return userRepository.findAll(PageRequest.of(page, rows)).getContent();
    }

    @PostConstruct
    public void initDb() {
        initDbService.initUsers();
    }
}
