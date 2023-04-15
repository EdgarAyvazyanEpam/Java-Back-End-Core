package com.epam.jmp.application;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.cloud.bank.impl.BankImpl;
import com.epam.jmp.cloud.service.impl.ServiceImpl;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.service.api.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        User edgar = new User("Edgar", "Ayvazyan", LocalDate.of(2000,12,12));
        User anna = new User("Anna", "Smith", LocalDate.of(2016,12,21));
        User john = new User("John", "Armas", LocalDate.of(2002,12,21));
        User bob = new User("Bob", "Doe", LocalDate.of(2002,12,21));

        Bank bank = new BankImpl();
        Service service = new ServiceImpl();
        BankCard creditCard = bank.createBankCard(edgar, BankCardType.CREDIT);
        BankCard creditCard2 = bank.createBankCard(john, BankCardType.CREDIT);
        BankCard debitCard = bank.createBankCard(anna, BankCardType.DEBIT);
        BankCard debitCard2 = bank.createBankCard(bob, BankCardType.DEBIT);
        service.subscribe(creditCard);
        service.subscribe(debitCard);
        service.subscribe(creditCard2);
        service.subscribe(debitCard2);

        Optional<Subscription> subscriptionByBankCardNumber = service.getSubscriptionByBankCardNumber(creditCard.getNumber());
        Subscription subscription = subscriptionByBankCardNumber
                .orElseThrow(() -> new SubscriptionNotFoundException("Cannot find subscription by card number:" + creditCard.getNumber()));
        Optional<Subscription> subscriptionByBankCardNumber2 = service.getSubscriptionByBankCardNumber(debitCard.getNumber());
        Subscription subscription1 = subscriptionByBankCardNumber2
                .orElseThrow(() -> new SubscriptionNotFoundException("Cannot find subscription by card number:" + debitCard.getNumber()));

        for (Optional<Subscription> optionalSubscription : Arrays.asList(subscriptionByBankCardNumber, subscriptionByBankCardNumber2)) {
            optionalSubscription.ifPresent(out::println);
        }
        out.println("Average is:" + service.getAverageUserAge());

        List<Subscription> subscriptions = Stream.of(subscription, subscription1)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        out.println("There is all subscriptions => " + subscriptions);

        List<User> users = service.getAllUsers()
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        out.println("There is all users " + users);

        List<List<User>> payableUsers = service.getAllPayableSubscriptionsByCondition().stream().toList();
        out.println("There is all payable users " + payableUsers);


    }
}