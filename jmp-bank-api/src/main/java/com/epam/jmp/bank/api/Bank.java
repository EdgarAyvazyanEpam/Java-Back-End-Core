package com.epam.jmp.bank.api;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.BankCardType;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}