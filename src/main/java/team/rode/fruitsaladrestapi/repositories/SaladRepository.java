package team.rode.fruitsaladrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.rode.fruitsaladrestapi.models.Salad;

import java.util.Optional;

@Repository
public interface SaladRepository extends JpaRepository<Salad, Long> {
    boolean existsByName(String name);
    Optional<Salad> findByName(String name);
}
