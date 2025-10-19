package io.github.ollama4j.examples.tools.toolfunctions;

import io.github.ollama4j.tools.ToolFunction;
import java.util.Map;

/**
 * ToolFunction implementation simulating a hotel booking.
 */
public class HotelBookingToolFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        String city = (String) arguments.get("city");
        String checkin = (String) arguments.get("checkin_date");
        String checkout = (String) arguments.get("checkout_date");
        int guests = ((Number) arguments.get("guests")).intValue();

        // Dummy booking confirmation logic
        return String.format(
                "Booking confirmed! Room number 10 booked for %d guest(s) in %s from %s to %s."
                        + " Booking ID is: HB-123)",
                guests, city, checkin, checkout);
    }
}
