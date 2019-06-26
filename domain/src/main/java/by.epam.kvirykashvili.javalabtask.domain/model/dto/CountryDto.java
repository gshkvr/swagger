package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDto {

    @ApiModelProperty(notes = "The database generated country ID")
    @NotNull(message = "Required for update, for create default - 0")
    private long id;

    @ApiModelProperty(notes = "Country name")
    @NotNull(message = "Country name should be filled")
    private String name;

    public static CountryDto fromCountryToDto(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    public static Country fromDtoToCountry(CountryDto countryDto) {
        return Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .build();
    }
}
