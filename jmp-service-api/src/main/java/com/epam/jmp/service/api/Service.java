package com.epam.jmp.service.api;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);
    Optional<Subscription> getSubscriptionByBankCardNumber(Predicate<Subscription> filter);
    List<User> getAllUsers();

    default double getAverageUserAge() {
       return getAllUsers().stream()
                .mapToDouble(u -> u.birthday().toEpochDay())
                .average()
                .orElse(0);
    }

    static boolean isPayable(User user) {
        return  user.birthday().isBefore(LocalDate.now().minusYears(18));
    }

    Optional<List<User>> getAllPayableSubscriptionsByCondition();
}
