package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat ;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findUserByDocumentsCase1() {
        String document = "22222222201";
        UserDto data = new UserDto("Guilherme", "Correa", document, new BigDecimal(10), "teste@email.com", "12345", UserType.COMMON);
        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocuments(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void findUserByDocumentsCase2() {
        String document = "22222222201";

        Optional<User> result = this.userRepository.findUserByDocuments(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDto data){
        User newUser =  new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}