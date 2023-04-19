package com.epam.jmp.application;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.service.api.Service;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        var service = ServiceLoader.load(Service.class).iterator().next();
        var bank = ServiceLoader.load(Bank.class).iterator().next();
        var user = new BankUser(bank,service);

        user.createUsers();
    }
}