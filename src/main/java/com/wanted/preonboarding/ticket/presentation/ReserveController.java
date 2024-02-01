package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.vo.ReserveInfo;
import com.wanted.preonboarding.ticket.presentation.dto.ReserveCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ResponseHandler<ReserveInfo>> createReservation(@RequestBody ReserveCreateRequest request) {
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

        ReserveInfo completedReserveInfo = ticketSeller.reserve(reserveInfo);
        return ResponseEntity.ok()
                .body(
                        ResponseHandler.<ReserveInfo>builder()
                                .statusCode(HttpStatus.OK)
                                .message("Success")
                                .data(completedReserveInfo)
                                .build()
                );
        }

    @GetMapping
    public void getAllReservations() {

    }

    public void alarmReservationIsAvailable() {

    }

}
