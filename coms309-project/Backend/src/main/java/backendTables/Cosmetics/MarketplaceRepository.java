package backendTables.Cosmetics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Integer> {
    Marketplace findById(int id);
}
