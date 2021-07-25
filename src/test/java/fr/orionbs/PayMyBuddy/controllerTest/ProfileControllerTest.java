package fr.orionbs.PayMyBuddy.controllerTest;

import fr.orionbs.PayMyBuddy.controller.FriendsController;
import fr.orionbs.PayMyBuddy.controller.ProfileController;
import fr.orionbs.PayMyBuddy.dto.FriendDTO;
import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @InjectMocks
    ProfileController profileController;

    @Mock
    UserServiceImpl userService;

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
    public void testProfile() throws Exception {

        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        mockMvc.perform(get("/profile")
                .session(mockHttpSession))
                .andExpect(status().isOk());
    }

    @Test
    public void testProfileRedirection() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isFound());
    }

    @Test
    public void testUpdateProfileController() {

        //GIVEN
        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        User user = User.builder().id(1).email("test2@email.com").firstName("test2").lastName("Test2").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        UserDTO userDTO = UserDTO.builder().email("test2@email.com").firstName("test2").lastName("Test2").build();

        when(userService.updateUserDTO(userDTO)).thenReturn(user);

        //WHEN
        profileController.updateProfile(userDTO,mockHttpSession);


        //THEN
        verify(userService, Mockito.times(1)).updateUserDTO(userDTO);
    }

    @Test
    public void testUpdateProfileWithoutUserSessionController() {

        //GIVEN
        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();

        User user = User.builder().id(1).email("test2@email.com").firstName("test2").lastName("Test2").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        UserDTO userDTO = UserDTO.builder().email("test2@email.com").firstName("test2").lastName("Test2").build();


        //WHEN
        profileController.updateProfile(userDTO,mockHttpSession);


        //THEN
        verify(userService, Mockito.times(0)).updateUserDTO(userDTO);
    }
}
