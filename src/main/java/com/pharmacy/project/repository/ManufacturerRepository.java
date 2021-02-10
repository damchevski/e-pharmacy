package com.pharmacy.project.repository;

import com.pharmacy.project.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query("SELECT m FROM Manufacturer m WHERE m.nMedicines = (SELECT MAX(nMedicines) FROM Manufacturer)")
    List<Manufacturer> getTop();
}
