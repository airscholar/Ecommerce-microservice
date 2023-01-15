package com.airscholar.CustomerService.command.api.service;

import com.airscholar.CustomerService.command.api.data.CardDetailsRepository;
import com.airscholar.CommonService.data.CardDetails;
import org.springframework.stereotype.Component;

@Component
public class CardService {

    private CardDetailsRepository cardDetailsRepository;

    public CardService(CardDetailsRepository cardDetailsRepository) {
        this.cardDetailsRepository = cardDetailsRepository;
    }

    public CardDetails create(CardDetails cardDetails){
        return cardDetailsRepository.save(cardDetails);
    }
}
