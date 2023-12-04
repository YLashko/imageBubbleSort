package yl.main;

public class Util {
    public static int getValue(int pixelValue, int mode, int multiplier) {
        int alpha = (pixelValue & 0xFF000000) >> 24;
        int red = (pixelValue & 0x00FF0000) >> 16;
        int green = (pixelValue & 0x0000FF00) >> 8;
        int blue = (pixelValue & 0x000000FF);

        return switch (mode) {
            case Modes.sum -> (red + green + blue) / 3;
            case Modes.r -> (int) (red - ((double) multiplier / 200) * green - ((double) multiplier / 200) * blue);
            case Modes.g -> (int) (green - ((double) multiplier / 200) * red - ((double) multiplier / 200) * blue);
            case Modes.b -> (int) (blue - ((double) multiplier / 200) * green - ((double) multiplier / 200) * red);
            case Modes.a -> alpha;
            default -> 0;
        };
    }
}
