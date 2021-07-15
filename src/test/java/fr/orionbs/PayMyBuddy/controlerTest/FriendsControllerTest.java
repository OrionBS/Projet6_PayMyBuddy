package fr.orionbs.PayMyBuddy.controlerTest;

import fr.orionbs.PayMyBuddy.dto.FriendDTO;
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
public class FriendsControllerTest {

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
    public void testFriends() throws Exception {

        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        mockMvc.perform(get("/friends")
                .session(mockHttpSession))
                .andExpect(status().isOk());
    }

    @Test
    public void testFriendsRedirection() throws Exception {
        mockMvc.perform(get("/friends"))
                .andExpect(status().isFound());
    }

    @Test
    public void testFriendsPost() throws Exception {

        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        User user = User.builder().id(1).email("test2@email.com").firstName("test2").lastName("Test2").amount(2000f).friends(null).transactions(null).build();
        userRepository.save(user);

        FriendDTO friendDTO = FriendDTO.builder().user(user).build();

        mockMvc.perform(post("/friends")
                .session(mockHttpSession))
                .andExpect(status().isForbidden());
    }

}
