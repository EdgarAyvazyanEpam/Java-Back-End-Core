module application {
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jmp.dto;

    uses com.epam.jmp.bank.api.Bank;
    uses com.epam.jmp.service.api.Service;

}