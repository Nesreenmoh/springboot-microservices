package com.nesreen.fraud;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customer_id}")
    public FraudCheckResponse isFraudster(@PathVariable("customer_id") Integer customerId) {
        boolean isFraudulentCustomer =
                fraudCheckService.isFraudulentCustomer(customerId);
        log.info("Fraud check request for a customer {}",customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
