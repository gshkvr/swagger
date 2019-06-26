package by.epam.kvirykashvili.javalabtask.rest.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Hotel;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.HotelDto;
import by.epam.kvirykashvili.javalabtask.rest.RestService;
import by.epam.kvirykashvili.javalabtask.rest.error.EntityNotFoundException;
import by.epam.kvirykashvili.javalabtask.service.HotelService;
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
@RequestMapping("/rest/api/hotels")
@Api(value = "Travel agency", description = "Operations with hotels")
public class HotelRestService implements RestService<HotelDto> {

    private final HotelService hotelService;

    @Autowired
    public HotelRestService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    @ApiOperation(value = "Search a hotel with an ID", response = HotelDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find hotel"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{hotelId}", produces = "application/json")
    public @ResponseBody
    HotelDto getById(@PathVariable("hotelId") long hotelId) {
        Hotel hotel = hotelService.findById(hotelId);
        if (hotel == null) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " wasn't found");
        } else {
            return HotelDto.fromHotelToDto(hotel);
        }
    }

    @Override
    @ApiOperation(value = "View a list of hotels", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of hotels"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<HotelDto> getAll() {
        List<Hotel> hotelList = hotelService.readAll();
        return hotelList.stream().map(HotelDto::fromHotelToDto).collect(Collectors.toList());
    }

    @Override
    @ApiOperation(value = "Update hotel from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update hotel"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> update(@Valid @RequestBody HotelDto entity) {
        Hotel hotel = hotelService.findById(entity.getId());
        if (hotel == null) {
            throw new EntityNotFoundException("Hotel with id: " + entity.getId() + " wasn't found");
        } else {
            hotelService.create(HotelDto.fromDtoToHotel(entity));
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    @ApiOperation(value = "Add hotel from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add hotel"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> add(@Valid @RequestBody HotelDto entity) {
        hotelService.create(HotelDto.fromDtoToHotel(entity));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Delete hotel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete hotel"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/delete/{hotelId}")
    public ResponseEntity<String> delete(@PathVariable("hotelId") long hotelId) {
        Hotel hotel = hotelService.findById(hotelId);
        if (hotel == null) {
            throw new EntityNotFoundException("Hotel with id: " + hotelId + " wasn't found");
        } else {
            hotelService.delete(Hotel.builder().id(hotelId).build());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
