package com.pharmacy.project.service;

import com.pharmacy.project.model.DeliveryCompany;
import com.pharmacy.project.model.Manufacturer;

import java.util.List;

public interface DeliveryCompanyService {
    List<DeliveryCompany> getAll();

    DeliveryCompany get(Long deliveryCompanyId);

    DeliveryCompany editDc(Long id, String name, String country, Integer price);

    DeliveryCompany addDc(String name, String country, Integer price);

    DeliveryCompany findById(Long id);

    DeliveryCompany deleteDc(Long id);
}
