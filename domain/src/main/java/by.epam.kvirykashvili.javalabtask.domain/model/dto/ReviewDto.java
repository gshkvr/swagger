package by.epam.kvirykashvili.javalabtask.domain.model.dto;

import by.epam.kvirykashvili.javalabtask.domain.model.Review;
import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    @ApiModelProperty(notes = "The database generated review ID")
    @NotNull(message = "Required for update, for create default - 0")
    private long id;

    @ApiModelProperty(notes = "Date of the review")
    @NotNull(message = "Review date should be selected")
    private Date date;

    @ApiModelProperty(notes = "Text of the review")
    @NotNull(message = "Review text should be filled")
    private String text;

    @ApiModelProperty(notes = "Id of user which leaves the review")
    @NotNull(message = "User id should be filled")
    private long userId;

    @ApiModelProperty(notes = "Name of user which leaves the review")
    @NotNull(message = "User name should be filled")
    private String userName;

    @ApiModelProperty(notes = "Id of tour on which leaved review")
    @NotNull(message = "Tour id should be filled")
    private long tourId;

    @ApiModelProperty(notes = "Description of tour on which leaved review")
    @NotNull(message = "Tour description should be filled")
    private String tourDescription;

    public static ReviewDto fromReviewToDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .date(review.getDate())
                .text(review.getText())
                .userId(review.getUser().getId())
                .userName(review.getUser().getLogin())
                .tourId(review.getTour().getId())
                .tourDescription(review.getTour().getDescription())
                .build();
    }

    public static Review fromDtoToReview(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .date(reviewDto.getDate())
                .text(reviewDto.getText())
                .user(User.builder().id(reviewDto.getUserId()).build())
                .tour(Tour.builder().id(reviewDto.getTourId()).build())
                .build();
    }

}
