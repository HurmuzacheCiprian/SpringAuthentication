package org.hc.authentication.dto;

import lombok.*;

/**
 * Created by roxana on 27.04.2016.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String message;
}
