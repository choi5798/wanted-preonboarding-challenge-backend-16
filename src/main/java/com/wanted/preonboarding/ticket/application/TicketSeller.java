package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.vo.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.vo.ReserveInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {

    private final PerformanceRepository performanceRepository;

    private final ReservationRepository reservationRepository;

    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findAll()
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public ReserveInfo reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 행사입니다."));

        String enableReserve = info.getIsReserve();
        if (!enableReserve.equalsIgnoreCase("enable")) {
            throw new IllegalStateException("예약할 수 없는 행사입니다.");
        }

        // 1. 결제
        int price = info.getPrice();
        validatePrice(reserveInfo.getAmount(), price);
        reserveInfo.setAmount(reserveInfo.getAmount() - price);
        // 2. 예매 진행
        reservationRepository.save(Reservation.of(reserveInfo));
        return reserveInfo;
    }

    private void validatePrice(long amount, int price) {
        if (amount < price) {
            throw new IllegalStateException("잔고가 부족합니다.");
        }
    }

}
