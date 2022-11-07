package eu.heisenbug.commoditiescharts.utils;

import java.text.DecimalFormat;

public final class Math {

    private Math() {
    }

    public static double invertValue(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(1 / value));
    }
}
