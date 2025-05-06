package com.financial.dto;

import java.time.Instant;

public record UserDTO(Long id,
                      String name,
                      String email,
                      Instant createdAt) {

}
