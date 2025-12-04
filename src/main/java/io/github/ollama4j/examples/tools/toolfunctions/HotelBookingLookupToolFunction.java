package io.github.ollama4j.examples.tools.toolfunctions;

import io.github.ollama4j.tools.ToolFunction;
import java.util.Map;

/**
 * ToolFunction implementation for looking up booking by ID.
 */
public class HotelBookingLookupToolFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        String bookingID = (String) arguments.get("bookingId");
        return String.format(
                "Found a booking with ID: %s. Booked for 2 guests. Enjoy your stay!", bookingID);
    }
}
