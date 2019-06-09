package fxapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartStringConverter extends javafx.util.StringConverter<Number> {
    @Override
    public String toString(Number t) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(t.longValue()));
    }

    @Override
    public Number fromString(String string) {
        throw new UnsupportedOperationException("Not supported.");
    }
}