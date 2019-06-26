package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.TourType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDto {

    @ApiModelProperty(notes = "The database generated tour ID")
    @NotNull(message = "Required for update, for create default - 0")
    private long id;

    @ApiModelProperty(notes = "Country of tour")
    @NotNull(message = "Country should be selected")
    private String country;

    @ApiModelProperty(notes = "Country of tour")
    @NotNull(message = "Country should be selected")
    private Long countryId;

    @ApiModelProperty(notes = "Date of tour")
    @NotNull(message = "Date should be selected")
    private Date date;

    @ApiModelProperty(notes = "Duration of tour")
    @NotNull(message = "Duration should be filled")
    private int duration;

    @ApiModelProperty(notes = "Description of tour")
    @NotNull(message = "Description should be filled")
    private String description;

    @ApiModelProperty(notes = "Hotel of tour")
    @NotNull(message = "Hotel should be selected")
    private String hotelName;

    @ApiModelProperty(notes = "Hotel of tour")
    @NotNull(message = "Hotel should be selected")
    private long hotelId;

    @ApiModelProperty(notes = "Hotel stars")
    @Min(1)
    @Max(5)
    @NotNull(message = "Stars should be filled")
    private int stars;

    @ApiModelProperty(notes = "Type of tour")
    @NotNull(message = "Tour type should be selected")
    private TourType tourType;

    @ApiModelProperty(notes = "Cost of tour")
    @Min(1)
    @NotNull(message = "Cost should be filled")
    private int cost;

    public static TourDto fromTourToDto(Tour tour) {
        return TourDto.builder()
                .id(tour.getId())
                .country(tour.getCountry().getName())
                .countryId(tour.getCountry().getId())
                .date(tour.getDate())
                .duration(tour.getDuration())
                .description(tour.getDescription())
                .hotelId(tour.getHotel().getId())
                .hotelName(tour.getHotel().getName())
                .stars(tour.getHotel().getStars())
                .tourType(tour.getTourType())
                .cost(tour.getCost())
                .build();
    }

    public static Tour fromDtoToTour(TourDto tourDto){
        return Tour.builder()
                .id(tourDto.getId())
                .country(Country.builder().id(tourDto.getCountryId()).build())
                .date(tourDto.getDate())
                .duration(tourDto.getDuration())
                .description(tourDto.getDescription())
                .hotel(Hotel.builder().id(tourDto.getHotelId()).build())
                .tourType(tourDto.getTourType())
                .cost(tourDto.getCost())
                .build();
    }
}
