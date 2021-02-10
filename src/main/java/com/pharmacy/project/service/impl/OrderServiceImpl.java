package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.DeliveryCompany;
import com.pharmacy.project.model.Order;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.model.User;
import com.pharmacy.project.model.exceptions.DeliveryCompanyNotFoundException;
import com.pharmacy.project.model.exceptions.UsernameNotFoundException;
import com.pharmacy.project.repository.DeliveryCompanyRepository;
import com.pharmacy.project.repository.OrderRepository;
import com.pharmacy.project.repository.ProductRepository;
import com.pharmacy.project.repository.UserRepository;
import com.pharmacy.project.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, DeliveryCompanyRepository deliveryCompanyRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.deliveryCompanyRepository = deliveryCompanyRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order placeOrder(String username, String toAddress, List<Product> products, Long deliveryCompanyId) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UsernameNotFoundException::new);

        DeliveryCompany deliveryCompany = this.deliveryCompanyRepository.findById(deliveryCompanyId)
                .orElseThrow(DeliveryCompanyNotFoundException::new);

        Order order = new Order(user, toAddress, products, deliveryCompany);
        this.orderRepository.save(order);

        Double balance = 0.0;
        for (Product p : products) {
            if (p.minusQuantity() == 0)
                this.productRepository.delete(p);
            else
                this.productRepository.save(p);

            balance += p.getPrice();
        }

        balance += deliveryCompany.getPrice();

        user.minusBalance(balance);
        this.userRepository.save(user);

        return order;
    }

    @Override
    public List<String> getAllDates() {
        List<String> allDates = new ArrayList<>();

        for (Order o : this.orderRepository.findAll()) {
            if (!allDates.contains(o.getDateCreated()))
                allDates.add(o.getDateCreated());
        }

        return allDates;
    }

    @Override
    public List<Integer> getProdByDate() {
        List<Integer> prodByDate = new ArrayList<>();

        for (String date : this.getAllDates()) {
            prodByDate.add(this.orderRepository.findAll()
                    .stream().filter(o -> o.getDateCreated().equals(date))
                    .mapToInt(Order::nOfProds).sum());
        }

        return prodByDate;
    }
}
