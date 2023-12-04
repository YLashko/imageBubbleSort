package yl.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcess {
    String imagesFolderIn = System.getProperty("user.dir") + "/images/in/";
    String imagesFolderOut = System.getProperty("user.dir") + "/images/out/";

    private BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(new File(imagesFolderIn + name));
    }

    private void saveImage(BufferedImage image, String fileName) {
        File f = new File(imagesFolderOut + fileName);
        try {
            ImageIO.write(image, "png", f);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public BufferedImage processImage(BufferedImage image, int mode, int axis, boolean reverse, boolean reverseDirection, int iter, int threshold, int multiplier) {
        BufferedImage imageOut =
                new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        int width = image.getWidth();
        int height = image.getHeight();
        int progressInterval = iter / 100;
        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);
        for (int i = 0; i < iter; i++) {
            int dirX, dirY;
            if (axis == 0) {
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width - 1; y++) {
                        dirX = reverseDirection ? height - x - 1 : x;
                        dirY = reverseDirection ? width - y - 2 : y;
                        swapPixelsIfBigger(imagePixels, dirX * width + dirY, dirX * width + dirY + 1, mode, reverse, threshold, multiplier);
                    }
                }
            } else {
                for (int x = 0; x < height - 1; x++) {
                    for (int y = 0; y < width; y++) {
                        dirX = reverseDirection ? height - x - 2 : x;
                        dirY = reverseDirection ? width - y - 1 : y;
                        swapPixelsIfBigger(imagePixels, dirX * width + dirY, (dirX + 1) * width + dirY, mode, reverse, threshold, multiplier);
                    }
                }
            }
        }
        imageOut.setRGB(0, 0, width, height, imagePixels, 0, width);
        return imageOut;
    }

    private void swapPixelsIfBigger(int[] arr, int pos1, int pos2, int mode, boolean reverse, int threshold, int multiplier) {
        int a = arr[pos1];
        int b = arr[pos2];
        if (((Util.getValue(b, mode, multiplier) - Util.getValue(a, mode, multiplier) < threshold)
            && Util.getValue(b, mode, multiplier) - Util.getValue(a, mode, multiplier) > 0
                && reverse) ||
            (Util.getValue(a, mode, multiplier) - Util.getValue(b, mode, multiplier) < threshold)
            && Util.getValue(a, mode, multiplier) - Util.getValue(b, mode, multiplier) > 0
                && !reverse) {
            arr[pos1] = b;
            arr[pos2] = a;
        }
    }

    public void process(String inName, String outName, int mode, int axis, boolean reverse, boolean reverseDir, int iters) throws IOException {
        BufferedImage inImage = loadImage(inName);
        BufferedImage outImage = processImage(inImage, mode, axis, reverse, reverseDir, iters, 0, 0);
        saveImage(outImage, outName);
    }

    public static void main(String[] args) throws IOException {

    }
}
