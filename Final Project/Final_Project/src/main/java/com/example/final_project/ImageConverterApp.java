package com.example.final_project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageConverterApp extends Application {

    private VBox imageList; // To show thumbnails and image details
    private ChoiceBox<String> formatSelector; // Dropdown for format selection
    private final List<File> selectedFiles = new ArrayList<>(); // Store selected images

    @Override
    public void start(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Button to upload images
        Button uploadButton = new Button("Upload Images");
        uploadButton.setOnAction(e -> uploadImages(stage));

        // Area to show thumbnails
        imageList = new VBox(10);

        // Dropdown for format selection
        formatSelector = new ChoiceBox<>();
        formatSelector.getItems().addAll("PNG", "JPEG", "BMP", "GIF");
        formatSelector.setValue("PNG");

        // Button to convert and download images
        Button convertButton = new Button("Convert Images");
        convertButton.setOnAction(e -> convertImages(stage));

        // Add all controls to the layout
        layout.getChildren().addAll(
                uploadButton,
                imageList,
                new Label("Choose Format:"),
                formatSelector,
                convertButton
        );

        // Set up the window
        Scene scene = new Scene(layout, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Image Converter");
        stage.show();
    }

    // Function to upload images
    private void uploadImages(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files != null) {
            selectedFiles.clear();
            imageList.getChildren().clear();

            for (File file : files) {
                try {
                    // Add file to the list
                    selectedFiles.add(file);

                    // Create a thumbnail
                    Image thumbnail = new Image(file.toURI().toString(), 100, 100, true, true);
                    ImageView thumbnailView = new ImageView(thumbnail);

                    // Show file name
                    Label nameInfo = new Label("Name: " + file.getName());

                    // Show file location
                    Label locationInfo = new Label("Location: " + file.getAbsolutePath());

                    // Show image height and width separately
                    String dimensions = getImageDimensions(file);
                    Label dimensionInfo = new Label(dimensions);

                    // Add thumbnail and details to the layout
                    VBox imageBox = new VBox(5, thumbnailView, nameInfo, locationInfo, dimensionInfo);
                    imageList.getChildren().add(imageBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Function to convert images
    private void convertImages(Stage stage) {
        if (selectedFiles.isEmpty()) {
            showAlert("Error", "No images uploaded!");
            return;
        }

        String targetFormat = formatSelector.getValue();

        FileChooser saveChooser = new FileChooser();
        saveChooser.setTitle("Save Converted Image");
        saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*." + targetFormat.toLowerCase()));

        for (File inputFile : selectedFiles) {
            try {
                File saveFile = saveChooser.showSaveDialog(stage);

                if (saveFile != null) {
                    if (!saveFile.getName().toLowerCase().endsWith("." + targetFormat.toLowerCase())) {
                        saveFile = new File(saveFile.getAbsolutePath() + "." + targetFormat.toLowerCase());
                    }

                    convertFile(inputFile, saveFile, targetFormat);
                    showAlert("Success", "Image saved: " + saveFile.getAbsolutePath());
                }
            } catch (IOException e) {
                showAlert("Error", "Failed to save image: " + e.getMessage());
            }
        }
    }

    // Function to convert a single image
    private void convertFile(File inputFile, File outputFile, String format) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        boolean success = ImageIO.write(image, format.toLowerCase(), outputFile);

        if (!success) {
            throw new IOException("Unsupported format: " + format);
        }
    }

    // Function to get image dimensions as "Height: [pixels], Width: [pixels]"
    private String getImageDimensions(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                return "Height: " + image.getHeight() + " pixels, Width: " + image.getWidth() + " pixels";
            } else {
                return "Height: Unknown, Width: Unknown";
            }
        } catch (IOException e) {
            return "Error reading image dimensions.";
        }
    }

    // Function to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
