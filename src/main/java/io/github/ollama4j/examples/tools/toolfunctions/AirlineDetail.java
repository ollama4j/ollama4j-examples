package io.github.ollama4j.examples.tools.toolfunctions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
class AirlineDetail {
    private String callsign;
    private String name;
    private String country;
}
