package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.User;
import by.epam.kvirykashvili.javalabtask.domain.model.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @ApiModelProperty(notes = "The database generated review ID")
    @NotNull(message = "Required for update, for create default - 0")
    private long id;

    @ApiModelProperty(notes = "Login of user")
    @NotNull(message = "User login should be filled")
    private String login;

    @ApiModelProperty(notes = "Password of user")
    @NotNull(message = "User password should be filled")
    private String password;

    @ApiModelProperty(notes = "Role of user")
    @NotNull(message = "User role should be selected")
    private UserRole role;

    public static UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getUserRole())
                .build();
    }

    public static User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .userRole(userDto.getRole())
                .build();
    }

}
