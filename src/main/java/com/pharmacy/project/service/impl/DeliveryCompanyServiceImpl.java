package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.DeliveryCompany;
import com.pharmacy.project.model.exceptions.DeliveryCompanyNotFoundException;
import com.pharmacy.project.repository.DeliveryCompanyRepository;
import com.pharmacy.project.service.DeliveryCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService {
    private final DeliveryCompanyRepository deliveryCompanyRepository;

    public DeliveryCompanyServiceImpl(DeliveryCompanyRepository deliveryCompanyRepository) {
        this.deliveryCompanyRepository = deliveryCompanyRepository;
    }

    @Override
    public List<DeliveryCompany> getAll() {
        return this.deliveryCompanyRepository.findAll();
    }

    @Override
    public DeliveryCompany get(Long deliveryCompanyId) {
        return this.deliveryCompanyRepository.findById(deliveryCompanyId)
                .orElseThrow(DeliveryCompanyNotFoundException::new);
    }

    @Override
    public DeliveryCompany editDc(Long id, String name, String country, Integer price) {
        DeliveryCompany dc = this.deliveryCompanyRepository.findById(id)
                .orElseThrow(DeliveryCompanyNotFoundException::new);

        dc.setCountry(country);
        dc.setName(name);
        dc.setPrice(price);

        this.deliveryCompanyRepository.save(dc);

        return dc;
    }

    @Override
    public DeliveryCompany addDc(String name, String country, Integer price) {
        DeliveryCompany dc = new DeliveryCompany(name, country, price);

        this.deliveryCompanyRepository.save(dc);
        return dc;
    }

    @Override
    public DeliveryCompany findById(Long id) {
        return this.deliveryCompanyRepository.findById(id)
                .orElseThrow(DeliveryCompanyNotFoundException::new);
    }

    @Override
    public DeliveryCompany deleteDc(Long id) {
        DeliveryCompany deliveryCompany = this.deliveryCompanyRepository.findById(id)
                .orElseThrow(DeliveryCompanyNotFoundException::new);
        this.deliveryCompanyRepository.delete(deliveryCompany);

        return deliveryCompany;
    }
}
