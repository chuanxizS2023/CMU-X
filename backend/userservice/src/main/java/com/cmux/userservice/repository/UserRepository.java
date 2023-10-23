package main.java.com.cmux.userservice.repository;

import main.java.com.cmux.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
