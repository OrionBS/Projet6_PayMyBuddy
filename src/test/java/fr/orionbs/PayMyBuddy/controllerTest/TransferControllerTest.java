package fr.orionbs.PayMyBuddy.controllerTest;

import fr.orionbs.PayMyBuddy.controller.RegistrationController;
import fr.orionbs.PayMyBuddy.controller.TransferController;
import fr.orionbs.PayMyBuddy.dto.TransactionDTO;
import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.TypeOfTransaction;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.impl.TransactionServiceImpl;
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
public class TransferControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @InjectMocks
    TransferController transferController;
    @Mock
    TransactionServiceImpl transactionService;

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
    public void testTransfer() throws Exception {

        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(200f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        mockMvc.perform(get("/transfer")
                .session(mockHttpSession))
                .andExpect(status().isOk());
    }

    @Test
    public void testTransferWithoutUserSession() throws Exception {

        mockMvc.perform(get("/transfer"))
                .andExpect(status().isFound());
    }

    @Test
    public void testFriendTransferPost() throws Exception {
        mockMvc.perform(post("/friendTransfer"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testBankTransferPost() throws Exception {
        mockMvc.perform(post("/bankTransfer"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFriendTransferedController() {

        //GIVEN
        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(2000f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        TransactionDTO transactionDTO = TransactionDTO.builder().typeOfTransaction(TypeOfTransaction.UserToUser).amount(20f).collectorEmail("test2@email.com").description("testTransfered").build();


        //WHEN
        transferController.friendTransfered(transactionDTO,mockHttpSession);


        //THEN
        verify(transactionService, Mockito.times(1)).userToUser(user.getEmail(),transactionDTO.getCollectorEmail(),transactionDTO.getAmount(),transactionDTO.getDescription());
    }

    @Test
    public void testFriendTransferedControllerWithoutUserSession() {

        //GIVEN
        MockHttpSession mockHttpSession = new MockHttpSession();

        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        TransactionDTO transactionDTO = TransactionDTO.builder().typeOfTransaction(TypeOfTransaction.UserToUser).amount(20f).collectorEmail("test2@email.com").description("testTransfered").build();


        //WHEN
        transferController.friendTransfered(transactionDTO,mockHttpSession);


        //THEN
        verify(transactionService, Mockito.times(0)).userToUser(user.getEmail(),transactionDTO.getCollectorEmail(),transactionDTO.getAmount(),transactionDTO.getDescription());
    }

    @Test
    public void testBankTransferedControllerTypeBankToUser() {

        //GIVEN
        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(2000f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        TransactionDTO transactionDTO = TransactionDTO.builder().typeOfTransaction(TypeOfTransaction.BankToUser).amount(20f).collectorEmail("test2@email.com").description("testTransfered").build();


        //WHEN
        transferController.bankTransfered(transactionDTO,mockHttpSession);


        //THEN
        verify(transactionService, Mockito.times(1)).bankToUser(user.getEmail(),transactionDTO.getAmount());
    }

    @Test
    public void testBankTransferedControllerTypeUserToBank() {

        //GIVEN
        UserSession userSession = UserSession.builder().idSession(1).emailSession("test@email.com").firstNameSession("test").lastNameSession("Test").amountSession(2000f).friends(null).transactions(null).build();

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userSession",userSession);

        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        TransactionDTO transactionDTO = TransactionDTO.builder().typeOfTransaction(TypeOfTransaction.UserToBank).amount(20f).collectorEmail("test2@email.com").description("testTransfered").build();


        //WHEN
        transferController.bankTransfered(transactionDTO,mockHttpSession);


        //THEN
        verify(transactionService, Mockito.times(1)).userToBank(user.getEmail(),transactionDTO.getAmount());
    }

    @Test
    public void testBankTransferedControllerWithoutUserSession() {

        //GIVEN
        MockHttpSession mockHttpSession = new MockHttpSession();

        User user = User.builder().id(1).email("test@email.com").firstName("test").lastName("Test").amount(2000f).friends(null).transactions(null).build();

        userRepository.save(user);

        TransactionDTO transactionDTO = TransactionDTO.builder().typeOfTransaction(TypeOfTransaction.UserToBank).amount(20f).collectorEmail("test2@email.com").description("testTransfered").build();


        //WHEN
        transferController.bankTransfered(transactionDTO,mockHttpSession);


        //THEN
        verify(transactionService, Mockito.times(0)).userToBank(user.getEmail(),transactionDTO.getAmount());
    }
}
