package fr.orionbs.PayMyBuddy.controlerTest;

import fr.orionbs.PayMyBuddy.dto.FriendDTO;
import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").password("$2a$10$6TajU85/gVrGUm5fv5Z8beVF37rlENohyLk3BEpZJFi6Av9JNkw9O").amount(200f).friends(null).transactions(null).build();
        userRepository.save(user);
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteByEmail("test@email.com");
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginPost() throws Exception {
        UserDTO userDTO = UserDTO.builder().email("test@email.com").password("123456").build();

        mockMvc.perform(post("/friends")
                .content(userDTO.toString()))
                .andExpect(status().isForbidden());
    }
}
