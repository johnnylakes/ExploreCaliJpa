package com.example.explorecali_jpa.controller;

import com.example.explorecali_jpa.dto.RatingDto;
import com.example.explorecali_jpa.model.TourRating;
import com.example.explorecali_jpa.services.TourRatingServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static jdk.vm.ci.amd64.AMD64.k1;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingServices tourRatingService;

    public TourRatingController(TourRatingServices tourRatingService) {

        this.tourRatingService = tourRatingService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDto ratingDto){

        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value="tourId") int tourId, @RequestBody @Valid RatingDto ratingDto){
        return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),ratingDto.getScore(),ratingDto.getComment()));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable(value="tourId") int tourId, @PathVariable(value = "customerId") int customerId){
            tourRatingService.delete(tourId, customerId);
}

@ExceptionHandler(NoSuchElementException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public String return404(NoSuchElementException exception){
    return exception.getMessage();
}

@GetMapping
public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId){
    List<TourRating> tourRating.lookUpRatings(tourId);
    return tourRating.stream().map(RatingDto::new).toList();
}

@GetMapping("/average")
public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId){
    return Map.of(k1:"average", tourRatingService.getAverageScore(tourId));
}

}