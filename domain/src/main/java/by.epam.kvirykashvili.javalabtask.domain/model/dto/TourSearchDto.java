package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.TourType;
import by.epam.kvirykashvili.javalabtask.domain.parameters.TourSearchParameters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourSearchDto {
    private Long countryId;
    private Integer duration;
    private Long hotelId;
    private TourType tourType;
    private Integer cost;

    public static TourSearchParameters dtoToTourSearchParameters(TourSearchDto dto){
        return TourSearchParameters.builder()
                .countryId(dto.getCountryId())
                .duration(dto.getDuration())
                .hotelId(dto.getHotelId())
                .tourType(dto.getTourType())
                .cost(dto.getCost())
                .build();
    }
}
