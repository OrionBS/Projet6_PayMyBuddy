package fr.orionbs.PayMyBuddy.controlerTest;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LogOffControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(200f).friends(null).transactions(null).build();
        userRepository.save(user);
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteByEmail("test@email.com");
    }

    @Test
    public void testLogOff() throws Exception {

        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        mockMvc.perform(get("/logoff")
                .session(mockHttpSession))
                .andExpect(status().isFound());
    }

    @Test
    public void testLogOffRedirection() throws Exception {
        mockMvc.perform(get("/logoff"))
                .andExpect(status().isFound());
    }
}
