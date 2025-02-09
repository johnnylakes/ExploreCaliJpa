package com.example.explorecali_jpa.services;

import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.TourRating;
import com.example.explorecali_jpa.repo.TourRatingRepository;
import com.example.explorecali_jpa.repo.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class TourRatingServices{
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;


    public TourRatingServices(TourRatingRepository tourRatingRepository, TourRepository tourRepository){
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public TourRating createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException{
        return tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, score, comment));
    }

    public Optional<TourRating> lookUpTourById(int id){
        return tourRatingRepository.findById(id);
    }

    public List<TourRating> lookupAll(){
        return tourRatingRepository.findAll();
    }

    public List<TourRating> lookupRatings(int tourId) throws NoSuchElementException {
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId());

    }

    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException{
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tourRatingRepository.save(rating);
    }

    public TourRating updateSome(int tourId, Integer customerId, Optional<Integer> score, Optional<String> comment) throws NoSuchElementException{
        TourRating rating = verifyTourRating(tourId, customerId);
        score.ifPresent(s -> rating.setScore(s));
        comment.ifPresent(c -> rating.setComment(c));
        return tourRatingRepository.save(rating);
    }

    public TourRating verifyTourRating(int tourId, Integer customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request(" + tourId + " for customer" + customerId));
    }

    public Double getAverageScore(int tourId) throws NoSuchElementException{
        List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public void delete(int tourId, Integer customerId) throws NoSuchElementException{
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException{
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
    }
}