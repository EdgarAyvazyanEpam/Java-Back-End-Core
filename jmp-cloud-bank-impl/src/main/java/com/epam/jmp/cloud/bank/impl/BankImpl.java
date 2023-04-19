package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankImpl implements Bank {

    private Map<User, BankCard> creators = new HashMap<>();

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        var number = UUID.randomUUID().toString();
        BankCard bankCard = null;
        switch (cardType) {
            case CREDIT -> bankCard = new CreditBankCard(number, user);

            case DEBIT -> bankCard = new DebitCard(number, user);

            default -> throwIfTypeIsUnknown(number, user);
        }
        creators.put(user, bankCard);
        return bankCard;
    }

    private BankCard throwIfTypeIsUnknown(String s, User user) {
        throw new IllegalArgumentException("card type cannot be null");
    }
}
