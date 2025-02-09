package com.example.explorecali_jpa.services;

import com.example.explorecali_jpa.model.Difficulty;
import com.example.explorecali_jpa.model.Region;
import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.Region;
import com.example.explorecali_jpa.model.TourPackage;
import com.example.explorecali_jpa.repo.TourPackageRepository;
import com.example.explorecali_jpa.repo.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServices {

    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    private TourServices(TourPackageRepository tourPackageRepository, TourRepository tourRepository){
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }
    public Tour createTour(String tourPackageName, String title, String description,
                           String blurb, Integer price, String duration, String bullets, String keywords, Difficulty difficulty, Region region){
        TourPackage tourPackage = tourPackageRepository.findById(tourPackageName).orElseThrow(()->new RuntimeException("Tour package not found for id: " + tourPackageName));
        return  tourPackageRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }


    public List<Tour> lookUpByTourPackageCode(String code){

        return tourRepository.findByTourPackageCode(code);
    }

    public long total(){
        return tourRepository.count();
    }
}
