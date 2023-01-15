package com.airscholar.CustomerService.command.api.aggregate;

import com.airscholar.CustomerService.command.api.command.CreateCardDetailsCommand;
import com.airscholar.CustomerService.command.api.events.CreateCardDetailsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Aggregate
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CardDetailsAggregate {
    @AggregateIdentifier
    private String cardId;
    private String customerId;
    private String cardName;
    private String cardPan;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cardCvv;

    @CommandHandler
    public CardDetailsAggregate(CreateCardDetailsCommand createCardDetailsCommand){
        log.info("Handling CreateCardDetailsCommand {}", createCardDetailsCommand);

        CreateCardDetailsEvent event = new CreateCardDetailsEvent();

        BeanUtils.copyProperties(createCardDetailsCommand, event);

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CreateCardDetailsEvent event){
        this.cardId = event.getCardId();
        this.cardName = event.getCardName();
        this.customerId = event.getCustomerId();
        this.cardCvv = event.getCardCvv();
        this.cardPan = event.getCardPan();
        this.expiryMonth = event.getExpiryMonth();
        this.expiryYear = event.getExpiryYear();

    }
}
