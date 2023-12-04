package yl.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    String filename = null;
    double scaleFactor = 20d;
    private final String imagesFolder = "images/in/";
    private final String imagesOutFolder = "images/out/";
    int imageScaleW = 26;
    int imageScaleH = 18;
    ImageProcess ip = new ImageProcess();
    JFrame frame = new JFrame();
    JTextArea filenameInTextArea = new JTextArea();
    JTextArea filenameOutTextArea = new JTextArea("out.png");
    JSlider thresholdSlider = new JSlider(0, 255, 10);
    JSlider iterSlider = new JSlider(0, 200, 10);
    JSlider modeSlider = new JSlider(0, 4, 0);
    JSlider multiplierSlider = new JSlider(0, 100, 0);
    JCheckBox reverseSortCheckBox = new JCheckBox("Reverse sort");
    JCheckBox axisCheckBox = new JCheckBox("Change axis");
    JCheckBox reverseDirCheckBox = new JCheckBox("Reverse direction");
    JButton filePickButton = new JButton("Pick image");
    JButton saveToFile = new JButton("Save");
    JTextArea thresholdTextArea = new JTextArea("Threshold: 10");
    JTextArea iterTextArea = new JTextArea("Iterations: 10");
    JTextArea modeTextArea = new JTextArea("Mode: sum");
    JTextArea multiplierTextArea = new JTextArea("Multiplier: +0%");

    JLabel imageLeftHolder = new JLabel();
    JLabel imageRightHolder = new JLabel();
    ImageIcon imageLeft = new ImageIcon();
    ImageIcon imageRight = new ImageIcon();

    BufferedImage editingImage;
    BufferedImage editingImageScaled = new BufferedImage((int) (imageScaleW * scaleFactor), (int) (imageScaleH * scaleFactor), BufferedImage.TYPE_4BYTE_ABGR);
    BufferedImage previewImage = new BufferedImage((int) (imageScaleW * scaleFactor), (int) (imageScaleH * scaleFactor), BufferedImage.TYPE_4BYTE_ABGR);

    public UI() {
        placeUIElements();
        addUIElements();
    }

    private void addUIElements() {
        filePickButton.addActionListener(e -> {
            try {
                new FilePicker(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        saveToFile.addActionListener(e -> {
            processImage();
        });

        thresholdSlider.addChangeListener(e -> {
            updatePreviewImage();
            thresholdTextArea.setText("Threshold: " + thresholdSlider.getValue());
        });
        iterSlider.addChangeListener(e -> {
            updatePreviewImage();
            iterTextArea.setText("Iterations: " + iterSlider.getValue());
        });
        multiplierSlider.addChangeListener(e -> {
            updatePreviewImage();
            multiplierTextArea.setText("Multiplier: +" + multiplierSlider.getValue() + "%");
        });
        reverseSortCheckBox.addChangeListener(e -> {
            updatePreviewImage();
        });
        reverseDirCheckBox.addChangeListener(e -> {
            updatePreviewImage();
        });
        axisCheckBox.addChangeListener(e -> {
            updatePreviewImage();
        });
        modeSlider.addChangeListener(e -> {
            updatePreviewImage();
            modeTextArea.setText("Mode: " + switch (modeSlider.getValue()) {
                case 0 -> "Sum";
                case 1 -> "Red";
                case 2 -> "Green";
                case 3 -> "Blue";
                case 4 -> "Alpha";
                default -> "!Value";
            });
        });

        frame.add(filePickButton);
        frame.add(filenameInTextArea);
        frame.add(filenameOutTextArea);
        frame.add(reverseDirCheckBox);
        frame.add(reverseSortCheckBox);
        frame.add(axisCheckBox);
        frame.add(saveToFile);
        frame.add(thresholdSlider);
        frame.add(thresholdTextArea);
        thresholdTextArea.setEditable(false);
        frame.add(iterSlider);
        frame.add(iterTextArea);
        iterTextArea.setEditable(false);
        frame.add(modeSlider);
        frame.add(modeTextArea);
        modeTextArea.setEditable(false);
        frame.add(multiplierSlider);
        frame.add(multiplierTextArea);
        multiplierTextArea.setEditable(false);
        frame.add(imageLeftHolder);
        frame.add(imageRightHolder);
        frame.setSize((int) (52 * scaleFactor), (int) (26 * scaleFactor));
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void placeUIElements() {
        filePickButton.setBounds(0, 0, (int) (4 * scaleFactor), (int) (4 * scaleFactor));
        filenameInTextArea.setBounds((int) (4 * scaleFactor), 0, (int) (4 * scaleFactor), (int) (4 * scaleFactor));
        thresholdSlider.setBounds((int) (8 * scaleFactor), 0, (int) (6 * scaleFactor), (int) (3 * scaleFactor));
        thresholdTextArea.setBounds((int) (8 * scaleFactor), (int) (3 * scaleFactor), (int) (6 * scaleFactor), (int) (1 * scaleFactor));
        iterSlider.setBounds((int) (14 * scaleFactor), 0, (int) (6 * scaleFactor), (int) (3 * scaleFactor));
        iterTextArea.setBounds((int) (14 * scaleFactor), (int) (3 * scaleFactor), (int) (6 * scaleFactor), (int) (1 * scaleFactor));
        modeSlider.setBounds((int) (20 * scaleFactor), 0, (int) (6 * scaleFactor), (int) (3 * scaleFactor));
        modeTextArea.setBounds((int) (20 * scaleFactor), (int) (3 * scaleFactor), (int) (6 * scaleFactor), (int) (1 * scaleFactor));
        multiplierSlider.setBounds((int) (26 * scaleFactor), 0, (int) (6 * scaleFactor), (int) (3 * scaleFactor));
        multiplierTextArea.setBounds((int) (26 * scaleFactor), (int) (3 * scaleFactor), (int) (6 * scaleFactor), (int) (1 * scaleFactor));
        reverseDirCheckBox.setBounds((int) (32 * scaleFactor), 0, (int) (7 * scaleFactor), (int) (2 * scaleFactor));
        reverseSortCheckBox.setBounds((int) (32 * scaleFactor), (int) (2 * scaleFactor), (int) (7 * scaleFactor), (int) (2 * scaleFactor));
        axisCheckBox.setBounds((int) (40 * scaleFactor), (int) (2 * scaleFactor), (int) (6 * scaleFactor), (int) (2 * scaleFactor));
        filenameOutTextArea.setBounds((int) (40 * scaleFactor), 0, (int) (6 * scaleFactor), (int) (2 * scaleFactor));
        saveToFile.setBounds((int) (46 * scaleFactor), 0, (int) (4 * scaleFactor), (int) (4 * scaleFactor));
        imageLeftHolder.setBounds(0, (int) (5 * scaleFactor), (int) (imageScaleW * scaleFactor), (int) (imageScaleH * scaleFactor));
        imageRightHolder.setBounds((int) (imageScaleW * scaleFactor), (int) (5 * scaleFactor), (int) (imageScaleW * scaleFactor), (int) (imageScaleH * scaleFactor));
    }

    public void setFileName(String filename) {
        this.filename = filename;
        filenameInTextArea.setText(filename);
        loadImage(filename);
    }

    private void loadImage(String filename) {
        try {
            editingImage = ImageIO.read(new File(imagesFolder + filenameInTextArea.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        resizeToSmallImage();
        imageLeft = new ImageIcon(editingImageScaled);
        imageLeftHolder.setIcon(imageLeft);
    }

    private void resizeToSmallImage() {
        int scaleX = (int) ((imageScaleW * scaleFactor));
        int scaleY = (int) ((imageScaleH * scaleFactor));
        Image scaledImage = editingImage.getScaledInstance(scaleX, scaleY, Image.SCALE_AREA_AVERAGING);
        editingImageScaled.getGraphics().drawImage(scaledImage, 0, 0, null);
    }

    private void updatePreviewImage() {
        if (editingImageScaled == null || editingImage == null) {return;}

        double imageScaleFactor = (((double) editingImageScaled.getWidth() / (double) editingImage.getWidth() / 2) +
            ((double) editingImageScaled.getHeight() / (double) editingImage.getHeight() / 2));
        int iter = (int) (iterSlider.getValue() * imageScaleFactor);
        previewImage = ip.processImage(
            editingImageScaled,
            modeSlider.getValue(),
            axisCheckBox.isSelected() ? 0 : 1,
            reverseSortCheckBox.isSelected(),
            reverseDirCheckBox.isSelected(),
            iter,
            thresholdSlider.getValue(),
            multiplierSlider.getValue()
        );
        imageRight = new ImageIcon(previewImage);
        imageRightHolder.setIcon(imageRight);
    }

    private void processImage() {
        if (editingImageScaled == null || editingImage == null) {return;}

        BufferedImage processedImage = ip.processImage(
            editingImage,
            modeSlider.getValue(),
            axisCheckBox.isSelected() ? 0 : 1,
            reverseSortCheckBox.isSelected(),
            reverseDirCheckBox.isSelected(),
            iterSlider.getValue(),
            thresholdSlider.getValue(),
            multiplierSlider.getValue()
        );

        File f = new File(imagesOutFolder + filenameOutTextArea.getText());
        try {
            ImageIO.write(processedImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UI ui = new UI();
    }
}
