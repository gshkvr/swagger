package by.epam.kvirykashvili.javalabtask.rest.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.TourDto;
import by.epam.kvirykashvili.javalabtask.rest.RestService;
import by.epam.kvirykashvili.javalabtask.rest.error.EntityNotFoundException;
import by.epam.kvirykashvili.javalabtask.service.TourService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/tours")
@Api(value = "Travel agency", description = "Operations with tours")
public class TourRestService implements RestService<TourDto> {

    private final TourService tourService;

    @Autowired
    public TourRestService(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    @ApiOperation(value = "Search a tour with an ID", response = TourDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find tour"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{tourId}", produces = "application/json")
    public @ResponseBody
    TourDto getById(@PathVariable("tourId") long tourId) {
        Tour tour = tourService.findById(tourId);
        if (tour == null) {
            throw new EntityNotFoundException("Tour with id: " + tourId + " wasn't found");
        } else {
            return TourDto.fromTourToDto(tour);
        }
    }

    @Override
    @ApiOperation(value = "View a list of available tours", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of tours"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<TourDto> getAll() {
        List<Tour> tourList = tourService.readAll();
        return tourList.stream().map(TourDto::fromTourToDto).collect(Collectors.toList());
    }

    @Override
    @ApiOperation(value = "Update tour from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update tour"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> update(@Valid @RequestBody TourDto entity) {
        Tour tour = tourService.findById(entity.getId());
        if (tour == null) {
            throw new EntityNotFoundException("Tour with id: " + entity.getId() + " wasn't found");
        } else {
            tourService.create(TourDto.fromDtoToTour(entity));
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    @ApiOperation(value = "Add tour from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add tour"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> add(@Valid @RequestBody TourDto entity) {
        tourService.create(TourDto.fromDtoToTour(entity));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Delete tour")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete tour"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/delete/{tourId}")
    public ResponseEntity<String> delete(@PathVariable("tourId") long tourId) {
        Tour tour = tourService.findById(tourId);
        if (tour == null) {
            throw new EntityNotFoundException("Tour with id: " + tourId + " wasn't found");
        } else {
            tourService.delete(Tour.builder().id(tourId).build());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
