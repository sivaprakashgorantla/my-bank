package com.sivaprakash.loan.entity;

import java.util.EnumMap;
import java.util.Map;

public enum LoanType {
    HOME_LOAN, 
    PERSONAL_LOAN, 
    CAR_LOAN, 
    EDUCATION_LOAN;

    private static final Map<LoanType, Double> interestRates = new EnumMap<>(LoanType.class);

    static {
        interestRates.put(HOME_LOAN, 6.5);
        interestRates.put(PERSONAL_LOAN, 12.5);
        interestRates.put(CAR_LOAN, 8.0);
        interestRates.put(EDUCATION_LOAN, 5.5);
    }

    public double getInterestRate() {
        return interestRates.get(this);
    }

    public static void updateInterestRate(LoanType loanType, double newRate) {
        if (newRate <= 0) {
            throw new IllegalArgumentException("Interest rate must be positive");
        }
        interestRates.put(loanType, newRate);
    }
}
