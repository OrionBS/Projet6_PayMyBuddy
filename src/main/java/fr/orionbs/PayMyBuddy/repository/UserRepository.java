package fr.orionbs.PayMyBuddy.repository;

import fr.orionbs.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "")
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);
}
