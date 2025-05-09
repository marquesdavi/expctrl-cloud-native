package com.financial.feature.user.dto;

public record UserRequest(String name,
                          String email,
                          String passwordHash) {

}
