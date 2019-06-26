package by.epam.kvirykashvili.javalabtask.rest.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.User;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.TourDto;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.UserDto;
import by.epam.kvirykashvili.javalabtask.rest.RestService;
import by.epam.kvirykashvili.javalabtask.rest.error.EntityNotFoundException;
import by.epam.kvirykashvili.javalabtask.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/users")
@Api(value = "Travel agency", description = "Operations with users")
public class UserRestService implements RestService<UserDto> {

    private final UserService userService;

    @Autowired
    public UserRestService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @ApiOperation(value = "Search a user with an ID", response = TourDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find user"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{userId}", produces = "application/json")
    public @ResponseBody
    UserDto getById(@PathVariable("userId") long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id: " + userId + " wasn't found");
        } else {
            return UserDto.fromUserToDto(user);
        }
    }

    @Override
    @ApiOperation(value = "View a list of users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of users"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<UserDto> getAll() {
        List<User> userList = userService.readAll();
        return userList.stream().map(UserDto::fromUserToDto).collect(Collectors.toList());
    }

    @Override
    @ApiOperation(value = "Update user from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update user"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> update(@Valid @RequestBody UserDto entity) {
        User user = userService.findById(entity.getId());
        if (user == null) {
            throw new EntityNotFoundException("User with id: " + entity.getId() + " wasn't found");
        } else {
            userService.create(UserDto.fromDtoToUser(entity));
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    @ApiOperation(value = "Add user from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add user"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> add(@Valid @RequestBody UserDto entity) {
        userService.create(UserDto.fromDtoToUser(entity));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete user"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id: " + userId + " wasn't found");
        } else {
            userService.delete(User.builder().id(userId).build());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
