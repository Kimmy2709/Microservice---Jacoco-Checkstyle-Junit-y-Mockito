package com.example.Proyecto_II.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStatus {
    /**
     * Indica si el cliente tiene cuentas activas.
     */
    private boolean hasActiveAccounts;
}
