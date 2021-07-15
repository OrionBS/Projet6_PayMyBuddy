package fr.orionbs.PayMyBuddy.serviceTest;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
        //when(userRepository.findByEmail(anyString())).thenReturn(null);
        //when(userRepository.existsUserByEmail("d")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        userService.addUser(userDTO);

        //THEN
        //verify(userRepository,Mockito.times(1)).findByEmail(anyString());
        verify(userRepository, Mockito.times(1)).existsByEmail(anyString());
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testAddUserAlreadyIn() {
        //GIVEN
        UserDTO userDTO = new UserDTO("d","d","d","d");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        //WHEN
        userService.addUser(userDTO);

        //THEN
        verify(userRepository, Mockito.times(1)).existsByEmail(userDTO.getEmail());

    }

    /*@Test
    public void testAddFriend() {
        //GIVEN
        User user = User.builder().id(1).email("user@email.com").build();
        User friend = User.builder().id(2).email("friend@email.com").build();
        when(userRepository.findByEmail(friend.getEmail())).thenReturn(friend);
        when(userRepository.existsByEmail(friend.getEmail())).thenReturn(false);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        userService.addFriend(user.getEmail(),friend.getEmail());

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail("user@email.com");
        verify(userRepository, Mockito.times(1)).existsByEmail("friend@email.com");
        verify(userRepository, Mockito.times(1)).findByEmail("friend@email.com");
        verify(userRepository, Mockito.times(1)).save(user);
    }*/

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
