package io.github.ollama4j.examples.tools.toolfunctions;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("ALL")
@Data
@NoArgsConstructor
class AirlineDetail {
    private String callsign;
    private String name;
    private String country;

    public AirlineDetail(String callsign, String name, String country) {
        this.callsign = callsign;
        this.name = name;
        this.country = country;
    }
}
