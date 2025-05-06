package com.financial.feature.user;

import java.time.Instant;

public record UserDTO(Long id,
                      String name,
                      String email,
                      Instant createdAt) {

}
