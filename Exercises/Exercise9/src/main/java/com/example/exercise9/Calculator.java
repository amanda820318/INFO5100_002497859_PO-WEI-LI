package com.example.exercise9;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculator extends Application {

    private TextField display;
    private double firstNumber = 0;
    private String operator = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        display = new TextField();
        display.setPrefHeight(50);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(false);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                String buttonText = buttons[index++];
                Button button = new Button(buttonText);
                button.setPrefSize(60, 60);
                button.setOnAction(e -> handleButton(buttonText));
                grid.add(button, col, row);
            }
        }

        VBox root = new VBox(10, display, grid);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    private void handleButton(String text) {
        switch (text) {
            case "C" -> clear();
            case "=" -> calculate();
            case "+", "-", "*", "/" -> setOperator(text);
            default -> appendNumber(text);
        }
    }

    private void clear() {
        display.setText("");
        firstNumber = 0;
        operator = "";
    }

    private void setOperator(String op) {
        if (!display.getText().isEmpty()) {
            firstNumber = Double.parseDouble(display.getText());
            operator = op;
            display.setText("");
        }
    }

    private void calculate() {
        if (!operator.isEmpty() && !display.getText().isEmpty()) {
            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;
            switch (operator) {
                case "+" -> result = firstNumber + secondNumber;
                case "-" -> result = firstNumber - secondNumber;
                case "*" -> result = firstNumber * secondNumber;
                case "/" -> result = (secondNumber != 0) ? firstNumber / secondNumber : 0;
            }
            display.setText(String.valueOf(result));
            operator = "";
        }
    }

    private void appendNumber(String number) {
        display.setText(display.getText() + number);
    }
}
