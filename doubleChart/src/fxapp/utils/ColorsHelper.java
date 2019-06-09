package fxapp.utils;

import javafx.scene.paint.Color;

public class ColorsHelper {
    public static String getRGBFromColor(Color color) {
        return String.format("%d, %d, %d",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
