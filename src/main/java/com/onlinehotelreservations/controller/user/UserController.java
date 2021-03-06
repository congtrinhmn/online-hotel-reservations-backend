package com.onlinehotelreservations.controller.user;

import com.onlinehotelreservations.controller.authentication.AuthenticationMapper;
import com.onlinehotelreservations.controller.authentication.DTO.RegisterDTO;
import com.onlinehotelreservations.controller.user.DTO.UserDTO;
import com.onlinehotelreservations.service.UserService;
import com.onlinehotelreservations.shared.model.ApiData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;

    private final AuthenticationMapper authenticationMapper;

    @GetMapping("/{id}")
    public ApiData<UserDTO> getUserFollowId(@PathVariable("id") int id) {
        return new ApiData<>(this.userMapper.toUserDTO(this.userService.getUserFollowId(id)));
    }

    @GetMapping
    public ApiData<List<UserDTO>> getAllUser() {
        return new ApiData<>(this.userMapper.toUserDTOs(this.userService.getAllUser()));
    }

    @PutMapping()
    public ApiData<UserDTO> editUser(@RequestBody @Validated RegisterDTO registerDTO) {
        return new ApiData<>(this.userMapper.toUserDTO(this.userService
                .editUser(this.authenticationMapper.toUserEntity(registerDTO))));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        this.userService.deleteUserFollowId(id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}/status")
    public ApiData<UserDTO> changeStatus(@PathVariable("id") int id) {
        return new ApiData<>(this.userMapper.toUserDTO(this.userService.reverseStatusUserFollowId(id)));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search")
    public ApiData<List<UserDTO>> searchUsers(@RequestParam(name = "valueSearch") String valueSearch) {
        return new ApiData<>(this.userMapper.toUserDTOs(this.userService.searchUsers(valueSearch)));
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}/admin")
    public ApiData<UserDTO> upToAdmin(@PathVariable("id") int id) {
        return new ApiData<>(this.userMapper.toUserDTO(this.userService.upToAdminFollowId(id)));
    }
}
