package com.code.nagostamelapp.transactionList.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListResponseDTO implements Comparable<TransactionListResponseDTO> {
    private String date;
    private Float amount;
    private String description;
    private Integer institution_id;
    private String institution;
    private String status_payment;
    private String direction;
    private String amountStr;

    @Override
    public int compareTo(TransactionListResponseDTO o) {
        if (o != null) {
            return this.date.compareTo(o.getDate());
        }
        return -1;
    }
}
