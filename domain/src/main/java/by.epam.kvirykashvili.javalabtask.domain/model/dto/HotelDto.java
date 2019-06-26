package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.Features;
import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto {

    @ApiModelProperty(notes = "The database generated hotel ID")
    @NotNull(message = "Required for update, for create default - 0")
    private long id;

    @ApiModelProperty(notes = "Name of the hotel")
    @NotNull(message = "Hotel name should be filled")
    private String name;

    @ApiModelProperty(notes = "Stars of the hotel")
    @Min(value = 1, message = "Stars count should be bigger or equals 1")
    @Max(value = 5, message = "Stars count should be less or equals 5")
    @NotNull(message = "")
    private int stars;

    @ApiModelProperty(notes = "Website of hotel")
    @NotNull(message = "Hotel website should be filled")
    private String website;

    @ApiModelProperty(notes = "Latitude of hotel")
    @NotNull(message = "Hotel latitude should be filled")
    private String latitude;

    @ApiModelProperty(notes = "Longitude of hotel")
    @NotNull(message = "Hotel longitude should be filled")
    private String longitude;

    @ApiModelProperty(notes = "Features of the hotel")
    @NotNull(message = "Hotel features should be selected")
    private Features[] features;

    public static HotelDto fromHotelToDto(Hotel hotel) {
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .stars(hotel.getStars())
                .website(hotel.getWebsite())
                .latitude(hotel.getLatitude())
                .longitude(hotel.getLongitude())
                .features(hotel.getFeatures())
                .build();
    }

    public static Hotel fromDtoToHotel(HotelDto hotelDto) {
        return Hotel.builder()
                .id(hotelDto.getId())
                .name(hotelDto.getName())
                .stars(hotelDto.getStars())
                .website(hotelDto.getWebsite())
                .latitude(hotelDto.getLatitude())
                .longitude(hotelDto.getLongitude())
                .features(hotelDto.getFeatures())
                .build();
    }

}
