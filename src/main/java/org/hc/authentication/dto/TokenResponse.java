package org.hc.authentication.dto;

import lombok.*;

/**
 * Created by roxana on 27.04.2016.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    public String token;
}
