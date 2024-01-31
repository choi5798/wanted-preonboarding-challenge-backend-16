package com.wanted.preonboarding.ticket.presentation.dto;

public record ReserveCreateRequest(
        String name,
        String phoneNumber,
        int amount,
        String performanceId,
        int round,
        int seat

) {

}
