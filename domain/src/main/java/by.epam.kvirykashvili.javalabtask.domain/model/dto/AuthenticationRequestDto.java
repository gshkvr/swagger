package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthenticationRequestDto {
    @ApiModelProperty(notes = "User login for jwt")
    private String login;
    @ApiModelProperty(notes = "User password for jwt")
    private String password;
}
