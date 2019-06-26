package by.epam.kvirykashvili.javalabtask.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

//    @Test
//    @Transactional
//    public void createUserTest() {
//        User user = User.builder()
//                .login("tourist")
//                .password("124sqwead21")
//                .userRole(UserRole.MEMBER)
//                .build();
//        long countBeforeCreate = userRepository.readAll().size();
//        userRepository.create(user);
//        long countAfterCreate = userRepository.readAll().size();
//        assertEquals(1, countAfterCreate - countBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void readAllUsersTest() {
//        long usersNamed = userRepository.readAll().size();
//        long usersCriteria = userRepository.readList(UserSearchParameters.builder()
//                .build())
//                .size();
//        assertEquals(usersNamed, usersCriteria);
//    }
//
//    @Test
//    @Transactional
//    public void readUserTest() {
//        User user = userRepository.readList(UserSearchParameters.builder()
//                .id(1)
//                .build())
//                .get(0);
//        assertEquals("user", user.getLogin());
//    }
//
//    @Test
//    @Transactional
//    public void updateUserTest() {
//        User user = User.builder()
//                .id(5)
//                .login("newLogin")
//                .password("newPassword")
//                .userRole(UserRole.MEMBER)
//                .build();
//        userRepository.update(user);
//        User updated = userRepository.readList(UserSearchParameters.builder()
//                .id(user.getId())
//                .build())
//                .get(0);
//        assertEquals(user.getLogin(), updated.getLogin());
//    }
//
//    @Test
//    @Transactional
//    public void deleteUserTest() {
//        User user = User.builder()
//                .id(100)
//                .build();
//        long countBeforeDelete = userRepository.readAll().size();
//        userRepository.delete(user);
//        long countAfterDelete = userRepository.readAll().size();
//        assertEquals(1, countBeforeDelete - countAfterDelete);
//    }
}