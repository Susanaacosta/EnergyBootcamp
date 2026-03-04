package com.energy.Repository;

import com.energy.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    // Busco por email porque es unique campo en el modelo de la BD
    Optional<UserModel> findByEmail(String email);
}