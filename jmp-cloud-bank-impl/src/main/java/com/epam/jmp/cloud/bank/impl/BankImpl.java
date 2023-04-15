package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.BankCardType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    private Map<BankCardType, BiFunction<String, User, BankCard>> creators;

    public BankImpl() {
        creators = new HashMap<>();
        creators.put(BankCardType.CREDIT, CreditBankCard::new);
        creators.put(BankCardType.DEBIT, CreditBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        String number = UUID.randomUUID().toString();
        return creators.getOrDefault(cardType, this::throwIfTypeIsUnknown).apply(number,user);

//        if (BankCardType.CREDIT == cardType) {
//            return new CreditBankCard(number, user);
//        }else if (BankCardType.DEBIT == cardType){
//            return new DebitCard(number, user);
//        }else {
//            throw new IllegalArgumentException("unknown credit card type" + cardType);
//        }
//        if (cardType == null) {
//            throw new IllegalArgumentException("card type cannot be null");
//        }
//        switch (cardType) {
//            case DEBIT -> {
//                return new DebitCard(number, user);
//            }
//            case CREDIT -> {
//                return new CreditBankCard(number, user);
//            }
//            default ->
//                throw new IllegalArgumentException("unknown credit card type" + cardType);
//
//        }
    }

    private BankCard throwIfTypeIsUnknown(String s, User user) {
        throw new IllegalArgumentException("card type cannot be null");
    }
}
