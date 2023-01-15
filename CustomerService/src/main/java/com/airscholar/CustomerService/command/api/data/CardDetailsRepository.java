package com.airscholar.CustomerService.command.api.data;

import com.airscholar.CommonService.data.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
}
