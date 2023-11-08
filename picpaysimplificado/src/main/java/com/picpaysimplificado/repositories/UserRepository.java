package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
    * Utilizei o Optional pois ele pode ou não retornar um objeto do tipo User
    * */
    Optional<User> findUserByDocuments(String document);

    Optional<User> findUserById(Long id);


}
