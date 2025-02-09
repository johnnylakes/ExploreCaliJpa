package com.example.explorecali_jpa.repo;

import com.example.explorecali_jpa.model.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourPackageRepository extends JpaRepository<TourPackage,String> {
    optional<Tourpackage> findByName(String name);
}
