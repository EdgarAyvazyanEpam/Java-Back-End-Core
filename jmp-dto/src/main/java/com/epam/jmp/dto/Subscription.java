package com.epam.jmp.dto;

import java.time.LocalDate;

public record Subscription(String bankCard,LocalDate startDate) {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subscription{");
        sb.append("bankCard='").append(bankCard).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append('}');
        return sb.toString();
    }
}
