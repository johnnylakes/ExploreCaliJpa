package com.example.explorecali_jpa;

import com.example.explorecali_jpa.model.Difficulty;
import com.example.explorecali_jpa.model.Region;
import com.example.explorecali_jpa.model.TourPackage;
import com.example.explorecali_jpa.services.TourPackageServices;
import com.example.explorecali_jpa.services.TourServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExplorecaliJpaApplication implements CommandLineRunner {

	private final String TOUR_IMPORT_FILE = "ExploreCalifornia.json";

	@Autowired
	private TourPackageServices tourPackageServices;

	@Autowired
	private TourServices tourServices;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		createTourAllPackages();
		System.out.println("Persisted Packages = " + tourPackageServices.total());
		createToursFromFile(TOUR_IMPORT_FILE);
		System.out.println("Persisted Tours = " + tourServices.total());
	}

	private void createTourAllPackages(){
		tourPackageServices.createTourPackage("BC", "Backpack Cal");
		tourPackageServices.createTourPackage("CC", "California Calm");
		tourPackageServices.createTourPackage("CH", "California Hot Springs");
		tourPackageServices.createTourPackage("CY", "Cycle California");
		tourPackageServices.createTourPackage("DS", "From Desert To Sea");
		tourPackageServices.createTourPackage("KC", "Kids California");
		tourPackageServices.createTourPackage("NW", "Nature Watch");
		tourPackageServices.createTourPackage("SC", "Snowboard Cali");
		tourPackageServices.createTourPackage("TC", "Taste of California");
	}

	private void createToursFromFile(String fileToImport) throws IOException{
		TourFromFile.read(fileToImport).forEach(t ->
				tourServices.createTour(
						t.packageName(),
						t.title(),
						t.description(),
						t.blurb(),
						t.price(),
						t.length(),
						t.bullets(),
						t.keywords(),
						Difficulty.valueOf(t.difficulty()),
						Region.findByLabel(t.region())
				)
		);
	}

	record  TourFromFile(String packageName, String title, String description, String blurb, Integer price, String length, String bullets,
						 String keywords, String difficulty, String region){
		static List<TourFromFile> read(String fileToImport) throws IOException{
			return new ObjectMapper().readValue(new File(fileToImport), new TypeReference<List<TourFromFile>>() {});
		}
	}

}
