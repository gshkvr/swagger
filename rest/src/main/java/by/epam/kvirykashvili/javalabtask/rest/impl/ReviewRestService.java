package by.epam.kvirykashvili.javalabtask.rest.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Review;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.HotelDto;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.ReviewDto;
import by.epam.kvirykashvili.javalabtask.rest.RestService;
import by.epam.kvirykashvili.javalabtask.rest.error.EntityNotFoundException;
import by.epam.kvirykashvili.javalabtask.service.ReviewService;
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
@RequestMapping("/rest/api/reviews")
@Api(value = "Travel agency", description = "Operations with reviews")
public class ReviewRestService implements RestService<ReviewDto> {

    private final ReviewService reviewService;

    @Autowired
    public ReviewRestService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @ApiOperation(value = "Search a review with an ID", response = HotelDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find review"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{reviewId}", produces = "application/json")
    public @ResponseBody
    ReviewDto getById(@PathVariable("reviewId") long reviewId) {
        Review review = reviewService.findById(reviewId);
        if (review == null) {
            throw new EntityNotFoundException("Review with id: " + reviewId + " wasn't found");
        } else {
            return ReviewDto.fromReviewToDto(review);
        }
    }

    @Override
    @ApiOperation(value = "View a list of reviews", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of reviews"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<ReviewDto> getAll() {
        List<Review> reviewList = reviewService.readAll();
        return reviewList.stream().map(ReviewDto::fromReviewToDto).collect(Collectors.toList());
    }

    @Override
    @ApiOperation(value = "Update review from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update review"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> update(@Valid @RequestBody ReviewDto entity) {
        Review review = reviewService.findById(entity.getId());
        if (review == null) {
            throw new EntityNotFoundException("Review with id: " + entity.getId() + " wasn't found");
        } else {
            reviewService.create(ReviewDto.fromDtoToReview(entity));
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    @ApiOperation(value = "Add review from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add review"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> add(@Valid @RequestBody ReviewDto entity) {
        reviewService.create(ReviewDto.fromDtoToReview(entity));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Delete review")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete review"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/delete/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable("reviewId") long reviewId) {
        Review review = reviewService.findById(reviewId);
        if (review == null) {
            throw new EntityNotFoundException("Review with id: " + reviewId + " wasn't found");
        } else {
            reviewService.delete(Review.builder().id(reviewId).build());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
