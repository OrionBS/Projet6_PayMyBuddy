package fr.orionbs.PayMyBuddy.repositoryTest;

import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        log.info("Repo Clean");
        userRepository.deleteByEmail("test@email.com");
    }

    @Test
    public void testFindByEmail() {
        //GIVEN
        User user = User.builder().email("test@email.com").build();
        userRepository.save(user);

        //WHEN
        User userFind = userRepository.findByEmail("test@email.com");

        //THEN
        assertThat(userFind).isNotNull();
        assertThat(userFind.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    public void testExistsByEmail() {
        //GIVEN
        User user = User.builder().email("test@email.com").build();
        userRepository.save(user);

        //WHEN
        Boolean exist = userRepository.existsByEmail("test@email.com");

        //THEN
        assertThat(exist).isEqualTo(true);
    }

    @Test
    public void testDeleteByEmail() {
        //GIVEN
        User user = User.builder().email("test@email.com").build();
        userRepository.save(user);

        //WHEN
        userRepository.deleteByEmail("test@email.com");

        //THEN
        assertThat(userRepository.findByEmail("test@email.com")).isNull();
    }

    @Test
    public void testFindByEmailAndPassword() {
        //GIVEN
        User user = User.builder().email("test@email.com").password("123456").build();
        userRepository.save(user);

        //WHEN
        User userFind = userRepository.findByEmailAndPassword("test@email.com","123456");

        //THEN
        assertThat(userFind).isNotNull();
        assertThat(userFind.getEmail()).isEqualTo("test@email.com");
        assertThat(userFind.getPassword()).isEqualTo("123456");

    }

}
