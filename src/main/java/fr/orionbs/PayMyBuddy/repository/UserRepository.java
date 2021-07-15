package fr.orionbs.PayMyBuddy.repository;

import fr.orionbs.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
