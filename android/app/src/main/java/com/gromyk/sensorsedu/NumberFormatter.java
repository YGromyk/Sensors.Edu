package com.gromyk.sensorsedu;

import java.text.DecimalFormat;

@SuppressWarnings("unused")
public class NumberFormatter {
    private static int decimals = 2;
    private static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat(getPattern(decimals));
    }


    public static String format(double number) {
        return decimalFormat.format(number);
    }

    private static String getPattern(int decimals) {
        StringBuilder pattern = new StringBuilder("0.");
        for (int i = 0; i < decimals; i++) {
            pattern.append("0");
        }
        return pattern.toString();
    }

    public static int getDecimals() {
        return decimals;
    }

    public static void setDecimals(int newDecimals) {
        decimals = newDecimals;
        decimalFormat.applyPattern(getPattern(decimals));
    }

}
