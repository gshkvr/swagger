package by.epam.kvirykashvili.javalabtask.rest.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Country;
import by.epam.kvirykashvili.javalabtask.domain.model.dto.CountryDto;
import by.epam.kvirykashvili.javalabtask.rest.RestService;
import by.epam.kvirykashvili.javalabtask.rest.error.EntityNotFoundException;
import by.epam.kvirykashvili.javalabtask.service.CountryService;
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
@RequestMapping("/rest/api/countries")
@Api(value = "Travel agency", description = "Operations with countries")
public class CountryRestService implements RestService<CountryDto> {

    private final CountryService countryService;

    @Autowired
    public CountryRestService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    @ApiOperation(value = "Search a country with an ID", response = CountryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find country"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{countryId}", produces = "application/json")
    public @ResponseBody CountryDto getById(@PathVariable("countryId") long countryId) {
        Country country = countryService.findById(countryId);
        if (country == null) {
            throw new EntityNotFoundException("Country with id: " + countryId + " wasn't found");
        } else {
            return CountryDto.fromCountryToDto(country);
        }
    }

    @Override
    @ApiOperation(value = "View a list of countries", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of countries"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<CountryDto> getAll() {
        List<Country> userList = countryService.readAll();
        return userList.stream().map(CountryDto::fromCountryToDto).collect(Collectors.toList());
    }

    @Override
    @ApiOperation(value = "Update country from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update country"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<String> update(@Valid @RequestBody CountryDto entity) {
        Country country = countryService.findById(entity.getId());
        if (country == null) {
            throw new EntityNotFoundException("Country with id: " + entity.getId() + " wasn't found");
        } else {
            countryService.create(CountryDto.fromDtoToCountry(entity));
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    @ApiOperation(value = "Add country from json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add country"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> add(@Valid @RequestBody CountryDto entity) {
        countryService.create(CountryDto.fromDtoToCountry(entity));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Delete tour")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete country"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/delete/{countryId}")
    public ResponseEntity<String> delete(@PathVariable("countryId") long countryId) {
        Country country = countryService.findById(countryId);
        if (country == null) {
            throw new EntityNotFoundException("Country with id: " + countryId + " wasn't found");
        } else {
            countryService.delete(Country.builder().id(countryId).build());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
