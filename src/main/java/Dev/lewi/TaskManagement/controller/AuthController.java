package Dev.lewi.TaskManagement.controller;

import Dev.lewi.TaskManagement.service.auth.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import Dev.lewi.TaskManagement.response.ApiResponse;
import Dev.lewi.TaskManagement.model.User;
import Dev.lewi.TaskManagement.dto.UserDto;
import Dev.lewi.TaskManagement.dto.RegisterRequest;
import Dev.lewi.TaskManagement.dto.LoginResponse;
import Dev.lewi.TaskManagement.dto.LoginRequest;
import Dev.lewi.TaskManagement.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Dev.lewi.TaskManagement.utils.exceptions.CustomExceptionResponse;

import static Dev.lewi.TaskManagement.utils.exceptions.ApiResponseUtils.REQUEST_ERROR_MESSAGE;
import static Dev.lewi.TaskManagement.utils.exceptions.ApiResponseUtils.REQUEST_SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final IUserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, userDto));
        } catch (CustomExceptionResponse e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.logInUser(request);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, response));
        } catch (CustomExceptionResponse e) {
            return ResponseEntity.status(FORBIDDEN).body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }
}
