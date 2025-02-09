package com.example.explorecali_jpa.services;

import com.example.explorecali_jpa.model.TourPackage;
import com.example.explorecali_jpa.repo.TourPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourPackageServices {

    private TourPackageRepository tourPackageRepository;

    public TourPackageServices(TourPackageRepository tourPackageRepository){
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code,String name){
        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public List<TourPackage> lookupAll(){
        return tourPackageRepository.findAll();
    }

    public long total(){
        return tourPackageRepository.count();
    }
}
