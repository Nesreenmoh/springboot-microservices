package com.nesreenco.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private  final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegisterationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if the email is valid
        //todo check if email not taken
        //todo: check if fraudster
        customerRepository.saveAndFlush(customer);

       FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customer_id}",
                 FraudCheckResponse.class,
                 customer.getId()
        );
          if(fraudCheckResponse.isFraudster()){
              throw new IllegalStateException("Fraudster");
          }
        // todo: store customer in db

        //todo: send notification
    }
}
