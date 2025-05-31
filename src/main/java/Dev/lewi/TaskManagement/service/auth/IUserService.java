package Dev.lewi.TaskManagement.service.auth;

import Dev.lewi.TaskManagement.dto.LoginRequest;
import Dev.lewi.TaskManagement.dto.LoginResponse;
import Dev.lewi.TaskManagement.dto.RegisterRequest;
import Dev.lewi.TaskManagement.dto.UserDto;
import Dev.lewi.TaskManagement.model.User;

import java.util.List;

public interface IUserService {
    User loadUserByUsername(String username);
    User createUser(RegisterRequest request);
    LoginResponse logInUser(LoginRequest request);
    UserDto convertUserToDto(User user);
}
