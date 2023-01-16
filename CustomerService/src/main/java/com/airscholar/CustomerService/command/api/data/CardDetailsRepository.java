package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CustomerService.command.api.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
}
