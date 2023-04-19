package com.epam.jmp.application;

import com.epam.jmp.application.exception.SubscriptionNotFoundException;
import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.cloud.bank.impl.BankImpl;
import com.epam.jmp.cloud.service.impl.ServiceImpl;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class BankUser {
    private final Bank bank;
    private final Service service;

    public BankUser(Bank bank, Service service) {
        this.bank = bank;
        this.service = service;
    }

    void createUsers() {
        var edgar = new User("Edgar", "Ayvazyan", LocalDate.of(2000,12,12));
        var anna = new User("Anna", "Smith", LocalDate.of(2016,12,21));
        var john = new User("John", "Armas", LocalDate.of(2002,12,21));
        var bob = new User("Bob", "Doe", LocalDate.of(2002,12,21));

        var creditCard = bank.createBankCard(edgar, BankCardType.CREDIT);
        var creditCard2 = bank.createBankCard(john, BankCardType.CREDIT);
        var debitCard = bank.createBankCard(anna, BankCardType.DEBIT);
        var debitCard2 = bank.createBankCard(bob, BankCardType.DEBIT);

        subscribe(creditCard);
        subscribe(debitCard);
        subscribe(creditCard2);
        subscribe(debitCard2);

        out.println("All users");
        getAllUsers();
        out.println("All Subscribers");
        getAllSubscriptions();
        out.println("All Payable Users");
        getAllPayableClients();
    }

    private void subscribe(BankCard bankCard) {
        try {
            service.subscribe(bankCard);
            Optional<Subscription> subscriptionByBankCardNumber = service.getSubscriptionByBankCardNumber(bankCard.getNumber());
            Subscription subscription = subscriptionByBankCardNumber
                    .orElseThrow(() -> new SubscriptionNotFoundException("Cannot find subscription by card number:" + bankCard.getNumber()));
            out.println("Subscription found:" + subscription.toString());
        }catch (SubscriptionNotFoundException e) {
            out.println("Could not find subscription for user");
        }
    }

     public void getAllSubscriptions () {
         ArrayList<List<Subscription>> subscriptions = service.getAllSubscriptions();

         out.println("There is all subscriptions => " + subscriptions);
     }

    public void getAllUsers () {
        List<User> users = service.getAllUsers()
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        out.println("There is all users " + users);
    }

    public void getAllPayableClients() {
        List<List<User>> payableUsers = service.getAllPayableSubscriptionsByCondition().stream().toList();
        out.println("There is all payable users " + payableUsers);
    }

}
