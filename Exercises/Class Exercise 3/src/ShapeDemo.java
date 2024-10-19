import java.io.*;

abstract class Shape implements Serializable {
    static String color = "Red";

    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    public static String getColor() {
        return color;
    }
}

class Triangle extends Shape {
    private double base;
    private double height;
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double base, double height, double sideA, double sideB, double sideC) {
        this.base = base;
        this.height = height;
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}

public class ShapeDemo {

    public static void main(String[] args) {
        Shape[] shapes = new Shape[4];
        shapes[0] = new Triangle(3, 4, 3, 4, 5);
        shapes[1] = new Rectangle(5, 10);
        shapes[2] = new Circle(7);
        shapes[3] = new Square(6);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shapes.ser"));
            oos.writeObject(shapes);
            oos.close();
            System.out.println("Shapes have been serialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("shapes.ser"));
            Shape[] deserializedShapes = (Shape[]) ois.readObject();
            ois.close();
            System.out.println("Shapes have been deserialized.");

            for (Shape shape : deserializedShapes) {
                System.out.println("Shape color: " + Shape.getColor());
                System.out.println("Area: " + shape.calculateArea());
                System.out.println("Perimeter: " + shape.calculatePerimeter());
                System.out.println("-------------");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
