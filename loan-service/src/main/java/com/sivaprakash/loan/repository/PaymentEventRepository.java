package com.sivaprakash.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivaprakash.loan.entity.PaymentEvent;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, Long> {

}
