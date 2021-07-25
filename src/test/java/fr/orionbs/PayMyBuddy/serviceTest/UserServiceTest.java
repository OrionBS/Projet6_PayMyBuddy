package fr.orionbs.PayMyBuddy.serviceTest;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    public void testGetUsers() {
        //GIVEN
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        //WHEN
        userService.getUsers();

        //THEN
        verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testIsUserPresent() {
        //GIVEN
        User user = User.builder().build();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //WHEN
        userService.isUserPresent(anyString());

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail(anyString());

    }
    @Test
    public void testIsNotUserPresent() {
        //GIVEN
        User user = User.builder().build();
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        //WHEN
        userService.isUserPresent(anyString());

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail(anyString());

    }

    @Test
    public void testUpdateUserDTO() {
        //GIVEN
        User user = User.builder().build();
        UserDTO userDTO = UserDTO.builder().email("test@email.com").build();
        when(userRepository.findByEmail("test@email.com")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        userService.updateUserDTO(userDTO);

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail("test@email.com");
        verify(userRepository, Mockito.times(1)).save(any(User.class));

    }

    @Test
    public void testUpdateUserData() {
        //GIVEN
        User user = User.builder().build();
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        userService.updateUserData(user);

        //THEN
        verify(userRepository, Mockito.times(1)).save(any(User.class));

    }

    @Test
    public void testAddUser() {
        //GIVEN
        User user = new User(null,"d","d","d","d",0,null,null);
        UserDTO userDTO = new UserDTO("d","d","d","d");
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        userService.addUser(userDTO);

        //THEN
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testAddUserAlreadyIn() {
        //GIVEN
        UserDTO userDTO = new UserDTO("d","d","d","d");

        User user = User.builder().id(1).email("d").firstName("d").lastName("d").amount(0f).friends(null).transactions(null).build();

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //WHEN
        userService.addUser(userDTO);

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail(userDTO.getEmail());

    }

    @Test
    public void testAddFriend() {
        //GIVEN
        User user = User.builder().id(1).email("user@email.com").friends(new ArrayList<>()).build();
        User friend = User.builder().id(2).email("friend@email.com").build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByEmail(friend.getEmail())).thenReturn(friend);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        Boolean result = userService.addFriend(user.getEmail(), friend.getEmail());

        //THEN
        assertEquals(result, true);
        verify(userRepository, Mockito.times(2)).findByEmail(valueCaptor.capture());
        assertEquals("user@email.com", valueCaptor.getAllValues().get(0));
        assertEquals("friend@email.com", valueCaptor.getAllValues().get(1));
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testAddFriendShouldReturnFalse() {
        //GIVEN
        User user = User.builder().id(1).email("user@email.com").friends(new ArrayList<>()).build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByEmail("friend@email.com")).thenReturn(null);

        //WHEN
        Boolean result = userService.addFriend(user.getEmail(), "friend@email.com");

        //THEN
        assertEquals(result, false);
        verify(userRepository, Mockito.times(2)).findByEmail(valueCaptor.capture());
        assertEquals("user@email.com", valueCaptor.getAllValues().get(0));
        assertEquals("friend@email.com", valueCaptor.getAllValues().get(1));
        verify(userRepository, Mockito.times(0)).save(user);
    }

    @Test
    public void testDeleteUsers() {
        //GIVEN

        //WHEN
        userService.deleteUsers();

        //THEN
        verify(userRepository,Mockito.times(1)).deleteAll();

    }

    @Test
    public void testFindUser() {
        //GIVEN
        User user = new User();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //WHEN
        userService.findUser(anyString());

        //THEN
        verify(userRepository,Mockito.times(1)).findByEmail(anyString());

    }

    @Test
    public void testFindUserWithEmailAndPassword() {
        //GIVEN
        User user = User.builder().email("test@email.com").password("$2a$10$6TajU85/gVrGUm5fv5Z8beVF37rlENohyLk3BEpZJFi6Av9JNkw9O").build();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //WHEN

        userService.findUserWithEmailAndPassword("test@email.com","123456");

        //THEN
        verify(userRepository,Mockito.times(1)).findByEmail(anyString());

    }

    @Test
    public void testFindUserWithUserNull() {
        //GIVEN
        User user = User.builder().email("test@email.com").password("$2a$10$6TajU85/gVrGUm5fv5Z8beVF37rlENohyLk3BEpZJFi6Av9JNkw9O").build();
        when(userRepository.findByEmail("test@email.com")).thenReturn(null);

        //WHEN

        userService.findUserWithEmailAndPassword("test@email.com","123456");

        //THEN
        verify(userRepository,Mockito.times(1)).findByEmail(anyString());

    }

    @Test
    public void testFindUserWithEmailAndPasswordWhenPasswordFalse() {
        //GIVEN
        User user = User.builder().email("test@email.com").password("$2a$10$6TajU85/gVrGUm5fv5Z4beVF37rlENohyLk3BEpZJFi6Av9JNkw9O").build();
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //WHEN

        userService.findUserWithEmailAndPassword("test@email.com","123456");

        //THEN
        verify(userRepository,Mockito.times(1)).findByEmail(anyString());

    }
}
//GIVEN

//WHEN

//THEN
