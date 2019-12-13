package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsername_whenUserExists_returnUser() {
        User user = new User();

        user.setUsername("test_user");
        user.setDisplayName("test_displayName");
        user.setPassword("P4ssword");

        testEntityManager.persist(user);

        User inDB = userRepository.findByUsername("test_user");
        assertThat(inDB).isNotNull();
    }

    public void findByUsername_whenUserDoesNotExist(){
        User inDB = userRepository.findByUsername("nonexistingUser");
        assertThat(inDB).isNull();
    }
}
