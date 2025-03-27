package team.rode.fruitsaladrestapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CalculationUtil {
    public static final double BASE_NUTRITION_UNIT = 100.0;
    public static final int DECIMAL_SCALE = 1;

    private CalculationUtil() {}

    public static double round(double value) {
        return new BigDecimal(value)
                .setScale(DECIMAL_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
