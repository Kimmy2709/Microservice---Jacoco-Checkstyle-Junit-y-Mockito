package com.example.Proyecto_II.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase que representa una cuenta bancaria.
 */
@Builder
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    /**
     * Identificador único de la cuenta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número único de la cuenta.
     */
    @Column(nullable = false, unique = true)
    private String accountNumber;

    /**
     * Monto actual en la cuenta.
     */
    @Column(nullable = false)
    private BigDecimal balanceAmount;

    /**
     * Tipo de cuenta (AHORROS o CORRIENTE).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    /**
     * Cliente asociado a esta cuenta.
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * Constructor vacío necesario para JPA.
     */
    public Account() {
    }

    /**
     * Constructor para inicializar todos los atributos de la cuenta.
     *
     * @param id           Identificador único de la cuenta.
     * @param accountNumber Número único de la cuenta.
     * @param balanceAmount Monto actual en la cuenta.
     * @param accountType   Tipo de cuenta (AHORROS o CORRIENTE).
     * @param client        Cliente asociado a esta cuenta.
     */
    public Account(final Long id, final String accountNumber, final BigDecimal balanceAmount,
                   final AccountType accountType, final Client client) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balanceAmount = balanceAmount;
        this.accountType = accountType;
        this.client = client;
    }

    /**
     * Enumeración de los tipos de cuenta.
     */
    enum AccountType {
        AHORROS, CORRIENTE
    }
}