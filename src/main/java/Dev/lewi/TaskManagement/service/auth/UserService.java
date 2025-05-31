package Dev.lewi.TaskManagement.service.auth;

import Dev.lewi.TaskManagement.dto.LoginRequest;
import Dev.lewi.TaskManagement.dto.LoginResponse;
import Dev.lewi.TaskManagement.dto.UserDto;
import Dev.lewi.TaskManagement.dto.RegisterRequest;
import Dev.lewi.TaskManagement.model.User;
import Dev.lewi.TaskManagement.repository.UserRepository;
import Dev.lewi.TaskManagement.security.JwtUtil;
import Dev.lewi.TaskManagement.utils.exceptions.CustomExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new CustomExceptionResponse("User not found with email: " + username));
    }


    @Override
    public User createUser(RegisterRequest request) {
        return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail())).map(req -> {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));  // Password encoding
            return userRepository.save(user);

        }).orElseThrow(() -> new CustomExceptionResponse("Oops!" + request.getEmail() + " already exists") );
    }

    @Override
    public LoginResponse logInUser(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            HashMap<String, Object> claims = new HashMap<>();
            String refreshToken = jwtUtil.generateRefreshToken(claims, userDetails);
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                    ()-> new CustomExceptionResponse("User Not found")
            );
            UserDto userDto = convertUserToDto(user);

            return LoginResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .user(userDto)
                    .build();
        } catch (AuthenticationException | CustomExceptionResponse e) {
            throw new CustomExceptionResponse(e.getMessage());
        }
    }


    @Override
    public UserDto convertUserToDto(User user){
        return mapper.map(user, UserDto.class);
    }
}

