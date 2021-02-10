package com.pharmacy.project.repository;

import com.pharmacy.project.model.DeliveryCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {
}
