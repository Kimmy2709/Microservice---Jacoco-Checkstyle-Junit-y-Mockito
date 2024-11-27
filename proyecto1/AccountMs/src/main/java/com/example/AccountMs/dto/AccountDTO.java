package com.example.AccountMs.dto;
import com.example.AccountMs.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {
    @JsonProperty("clientId")
    private Long client_id;
    @JsonProperty("accountType")
    private Account.AccountType account_type;
    @JsonProperty("balanceAmount")
    private BigDecimal balance_amount;
}
