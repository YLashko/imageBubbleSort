package yl.main;

public class Util {
    public static int getValue(int pixelValue, int mode) {
        int alpha = (pixelValue & 0xFF000000) >> 24;
        int red = (pixelValue & 0x00FF0000) >> 16;
        int green = (pixelValue & 0x0000FF00) >> 8;
        int blue = (pixelValue & 0x000000FF);

        return switch (mode) {
            case Modes.sum -> (red + green + blue) / 3;
            case Modes.r -> red;
            case Modes.g -> green;
            case Modes.b -> blue;
            case Modes.a -> alpha;
            default -> 0;
        };
    }
}
