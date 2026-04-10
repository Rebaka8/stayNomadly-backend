package com.staynomadly.api.config;

import com.staynomadly.api.entity.HomestayListing;
import com.staynomadly.api.entity.Review;
import com.staynomadly.api.entity.User;
import com.staynomadly.api.enums.ListingStatus;
import com.staynomadly.api.enums.Role;
import com.staynomadly.api.repository.HomestayListingRepository;
import com.staynomadly.api.repository.ReviewRepository;
import com.staynomadly.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UserRepository userRepository;
    private final HomestayListingRepository listingRepository;
    private final ReviewRepository reviewRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Only seed if empty
            if (userRepository.count() > 0 && listingRepository.count() >= 3) {
                log.info("Database already seeded.");
                return;
            }

            log.info("Seeding database with mock data...");

            // 1. Create Hosts and Tourist
            User host1 = new User();
            host1.setEmail("john.host@example.com");
            host1.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O"); // Dummy bcrypt
            host1.setFullName("John Host");
            host1.setRole(Role.HOMESTAY_HOST);
            userRepository.save(host1);

            User host2 = new User();
            host2.setEmail("maria.host@example.com");
            host2.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O");
            host2.setFullName("Maria Garcia");
            host2.setRole(Role.HOMESTAY_HOST);
            userRepository.save(host2);

            User host3 = new User();
            host3.setEmail("david.host@example.com");
            host3.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O");
            host3.setFullName("David Smith");
            host3.setRole(Role.HOMESTAY_HOST);
            userRepository.save(host3);

            User tourist = new User();
            tourist.setEmail("alice.tourist@example.com");
            tourist.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O");
            tourist.setFullName("Alice Tourist");
            tourist.setRole(Role.TOURIST);
            userRepository.save(tourist);

            User admin = new User();
            admin.setEmail("admin@staynomadly.com");
            admin.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O");
            admin.setFullName("System Admin");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User guide = new User();
            guide.setEmail("local.guide@example.com");
            guide.setPassword("$2a$10$r9b.jJ1L.Z7hM2J0pE561u0s7B7xP7A6m.0uY.x/Z/xX8I.N62M3O");
            guide.setFullName("Elena Guide");
            guide.setRole(Role.LOCAL_GUIDE);
            userRepository.save(guide);

            // 2. Create the exact property from the user's PropertyDetails UI reference!
            HomestayListing p1 = new HomestayListing();
            p1.setHost(host1);
            p1.setTitle("Rudra Bloom Premium 2BHK,10min from RGI Airport");
            p1.setDescription("Entire rental unit in Hyderabad, India.");
            p1.setCity("Hyderabad");
            p1.setArea("Shamshabad");
            p1.setPricePerNight(new BigDecimal("3080.15"));
            p1.setStatus(ListingStatus.ACTIVE);
            p1.setRating(4.82);
            p1.setDistance("10 kilometers away");
            p1.setIsGuestFavorite(true);
            p1.setCategory("Trending");
            p1.setPhotosUrls(Arrays.asList(
                    "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=1200&auto=format&fit=crop",
                    "https://images.unsplash.com/photo-1540518614846-7eded433c457?q=80&w=800&auto=format&fit=crop",
                    "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?q=80&w=800&auto=format&fit=crop",
                    "https://images.unsplash.com/photo-1556910103-1c02745aae4d?q=80&w=800&auto=format&fit=crop",
                    "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=800&auto=format&fit=crop"
            ));
            p1.setAmenities(Arrays.asList("Kitchen", "Wifi", "Free parking on premises", "TV"));
            listingRepository.save(p1);

            // 3. Create a second property: Bora Bora Beachfront
            HomestayListing p2 = new HomestayListing();
            p2.setHost(host2);
            p2.setTitle("Luxurious Overwater Bungalow in Bora Bora");
            p2.setDescription("An incredible beachfront and overwater experience.");
            p2.setCity("Bora Bora");
            p2.setArea("French Polynesia");
            p2.setPricePerNight(new BigDecimal("88000.00"));
            p2.setStatus(ListingStatus.ACTIVE);
            p2.setRating(4.99);
            p2.setDistance("7,800 kilometers away");
            p2.setIsGuestFavorite(true);
            p2.setCategory("Beachfront");
            p2.setPhotosUrls(Arrays.asList(
                    "https://images.unsplash.com/photo-1506929562872-bb421503ef21?q=80&w=1200&auto=format&fit=crop", // beach main
                    "https://images.unsplash.com/photo-1582268611958-ebfd161ef9cf?q=80&w=800&auto=format&fit=crop", // beach 2
                    "https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?q=80&w=800&auto=format&fit=crop", // beach 3
                    "https://images.unsplash.com/photo-1519046904884-53103b34b206?q=80&w=800&auto=format&fit=crop", // beach 4
                    "https://images.unsplash.com/photo-1439066615861-d1af74d74000?q=80&w=800&auto=format&fit=crop"  // beach 5
            ));
            p2.setAmenities(Arrays.asList("Wifi", "Pool", "Beach access"));
            listingRepository.save(p2);
            
            // 4. Create a regular Cabins property
            HomestayListing p3 = new HomestayListing();
            p3.setHost(host3);
            p3.setTitle("Swiss Alps Wooden Cabin");
            p3.setDescription("Wake up to the alps");
            p3.setCity("Zermatt");
            p3.setArea("Switzerland");
            p3.setPricePerNight(new BigDecimal("44000.00"));
            p3.setStatus(ListingStatus.ACTIVE);
            p3.setRating(4.95);
            p3.setDistance("5,300 kilometers away");
            p3.setIsGuestFavorite(true);
            p3.setCategory("Cabins");
            p3.setPhotosUrls(Arrays.asList(
                    "https://images.unsplash.com/photo-1449844908441-8829872d2607?q=80&w=1200&auto=format&fit=crop", // cabin main
                    "https://images.unsplash.com/photo-1542718610-a1d656d1884c?q=80&w=800&auto=format&fit=crop", // cabin snow
                    "https://images.unsplash.com/photo-1510798831971-661eb04b3739?q=80&w=800&auto=format&fit=crop", // cabin fire
                    "https://images.unsplash.com/photo-1448630360428-65456885c650?q=80&w=800&auto=format&fit=crop", // cabin wide
                    "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?q=80&w=800&auto=format&fit=crop"  // cabin mount
            ));
            p3.setAmenities(Arrays.asList("Heating", "Wifi"));
            listingRepository.save(p3);

            // 5. Create Reviews for P1! We need categorized reviews (e.g. Location, Cleanliness)
            String[] categories = {"Location", "Cleanliness", "Check-in", "Condition", "Hospitality", "Comfort", "Value"};
            for (String category : categories) {
                Review r1 = new Review();
                r1.setTourist(tourist);
                r1.setHomestayListing(p1);
                r1.setRating(5);
                r1.setCategory(category);
                r1.setComment("Absolutely amazing " + category.toLowerCase() + "!! The host was great. " + category + " exceeded all expectations. Will stay here again.");
                reviewRepository.save(r1);

                Review r2 = new Review();
                r2.setTourist(tourist);
                r2.setHomestayListing(p2);
                r2.setRating(4);
                r2.setCategory(category);
                r2.setComment("Pretty solid " + category.toLowerCase() + ". Could be improved slightly, but overall very satisfying experience.");
                reviewRepository.save(r2);
            }

            log.info("Database seeding completed.");
        };
    }
}
