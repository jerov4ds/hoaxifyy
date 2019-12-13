package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.shared.GenericResponse;
import com.hoaxify.hoaxify.user.UserRepository;
import com.hoaxify.hoaxify.user.UserService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import com.hoaxify.hoaxify.user.User;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    public static final String API_V_1_USERS = "/api/v1/users";
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;

    @Before
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_receive(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void postUser_whenUserIsValid_passwordIsHashedInDatabase(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        List<User> users = userRepository.findAll();
        User inDB = users.get(0);
        assertThat(inDB.getPassword()).isNotEqualTo(user.getPassword());
    }

    @Test
    public void postUser_whenUserIsNull_receiveBadRequest(){
        User user = createValidUser();
        user.setUsername(null);
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_V_1_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserIsValid_receiveSuccessMessage(){
        User user = createValidUser();
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_V_1_USERS, user, GenericResponse.class);
        assertThat(response.getBody()).isNotNull();
    }


    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("PASSWORD");
        return user;
    }
}
