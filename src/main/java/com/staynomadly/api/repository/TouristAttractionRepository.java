package com.staynomadly.api.repository;

import com.staynomadly.api.entity.TouristAttraction;
import com.staynomadly.api.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {
    List<TouristAttraction> findByCategory(Category category);
}
