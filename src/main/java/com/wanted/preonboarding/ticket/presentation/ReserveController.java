package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.vo.ReserveInfo;
import com.wanted.preonboarding.ticket.presentation.dto.ReserveCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {

    private final TicketSeller ticketSeller;

    @PostMapping
    public boolean reservation(@RequestBody ReserveCreateRequest request) {
        System.out.println("reservation");
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reservationName(request.name())
                .reservationPhoneNumber(request.phoneNumber())
                .reservationStatus("reserve")
                .amount(request.round())
                .performanceId(UUID.fromString(request.performanceId()))
                .round(request.round())
                .seat(request.seat())
                .build();

        return ticketSeller.reserve(reserveInfo);

//        return ticketSeller.reserve(ReserveInfo.builder()
//                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
//                .reservationName("유진호")
//                .reservationPhoneNumber("010-1234-1234")
//                .reservationStatus("reserve")
//                .amount(200000)
//                .round(1)
//                .line('A')
//                .seat(1)
//                .build()
//        );
    }

}
