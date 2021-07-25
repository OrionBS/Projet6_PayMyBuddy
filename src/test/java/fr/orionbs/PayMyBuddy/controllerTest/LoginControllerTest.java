package fr.orionbs.PayMyBuddy.controllerTest;

import fr.orionbs.PayMyBuddy.controller.LoginController;
import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @InjectMocks
    LoginController loginController;
    @Mock
    UserServiceImpl userService;

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

        mockMvc.perform(post("/friends"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testLoginTreatmentController() {

        //GIVEN
        MockHttpSession mockHttpSession = new MockHttpSession();

        User user = User.builder().id(1).email("test2@email.com").firstName("test2").lastName("Test2").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        UserDTO userDTO = UserDTO.builder().email("test2@email.com").firstName("test2").lastName("Test2").password("123456").build();

        when(userService.findUserWithEmailAndPassword(userDTO.getEmail(),userDTO.getPassword())).thenReturn(true);
        when(userService.findUser(userDTO.getEmail())).thenReturn(user);

        //WHEN
        loginController.loginTreatment(userDTO,mockHttpSession);


        //THEN
        verify(userService, Mockito.times(1)).findUser(userDTO.getEmail());
        verify(userService, Mockito.times(1)).findUserWithEmailAndPassword(userDTO.getEmail(),userDTO.getPassword());
    }

    @Test
    public void testLoginTreatmentWithoutBeingRegister() {

        //GIVEN
        MockHttpSession mockHttpSession = new MockHttpSession();

        UserDTO userDTO = UserDTO.builder().email("test2@email.com").firstName("test2").lastName("Test2").password("123456").build();

        when(userService.findUserWithEmailAndPassword(userDTO.getEmail(),userDTO.getPassword())).thenReturn(false);

        //WHEN
        loginController.loginTreatment(userDTO,mockHttpSession);

        //THEN
        verify(userService, Mockito.times(1)).findUserWithEmailAndPassword(userDTO.getEmail(),userDTO.getPassword());
    }
}
