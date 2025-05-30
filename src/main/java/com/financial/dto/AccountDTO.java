package com.financial.dto;

public record AccountDTO(Long id,
                        Long bankId,
                        Long userId,
                        String accountNumber,
                        String branch,
                        String currency) {

}
