package com.petnexusai.config;

import com.petnexusai.model.Breed;
import com.petnexusai.model.Species;
import com.petnexusai.repository.BreedRepository;
import com.petnexusai.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This component runs on application startup to seed the database
 * with initial data if it's empty.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;

    @Override
    public void run(String... args) throws Exception {
        if (speciesRepository.count() == 0) {
            log.info("Database is empty. Seeding initial data...");

            // Seed Species
            Species dog = new Species("Dog");
            Species cat = new Species("Cat");
            speciesRepository.saveAll(Arrays.asList(dog, cat));

            // Seed Dog Breeds
            breedRepository.saveAll(Arrays.asList(
                    new Breed("Labrador Retriever", dog),
                    new Breed("German Shepherd", dog),
                    new Breed("Golden Retriever", dog),
                    new Breed("Poodle", dog),
                    new Breed("Beagle", dog),
                    new Breed("Other", dog)
            ));

            // Seed Cat Breeds
            breedRepository.saveAll(Arrays.asList(
                    new Breed("Siamese", cat),
                    new Breed("Persian", cat),
                    new Breed("Maine Coon", cat),
                    new Breed("Ragdoll", cat),
                    new Breed("Sphynx", cat),
                    new Breed("Other", cat)
            ));

            log.info("✅ Initial data seeded successfully.");
        } else {
            log.info("Database already contains data. Skipping seeding.");
        }
    }
}