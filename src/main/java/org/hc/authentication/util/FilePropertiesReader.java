package org.hc.authentication.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by roxana on 24.04.2016.
 */
@Component
@Getter
public class FilePropertiesReader {

    @Value("${custom.header.authorization.name}")
    private String headerName;

    @Value("${custom.header.authorization.value}")
    private String headerValue;
}
