import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Student {
    protected String name;
    protected List<Integer> quizScores;

    public Student(String name) {
        this.name = name;
        this.quizScores = new ArrayList<>();
    }

    public void addQuizScore(int score) {
        if (quizScores.size() < 15) {
            quizScores.add(score);
        }
    }

    public double calculateAverageQuizScore() {
        if (quizScores.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int score : quizScores) {
            sum += score;
        }
        return (double) sum / quizScores.size();
    }

    public List<Integer> getQuizScores() {
        return quizScores;
    }

    public String getName() {
        return name;
    }
}

class PartTimeStudent extends Student {
    public PartTimeStudent(String name) {
        super(name);
    }
}

class FullTimeStudent extends Student {
    private int[] examScores;

    public FullTimeStudent(String name, int examScore1, int examScore2) {
        super(name);
        this.examScores = new int[]{examScore1, examScore2};
    }

    public int[] getExamScores() {
        return examScores;
    }
}

class Session {
    private List<Student> students;

    public Session() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (students.size() < 20) {
            students.add(student);
        }
    }

    public void calculateAverageQuizScores() {
        for (Student student : students) {
            System.out.println(student.getName() + " average quiz score: " + student.calculateAverageQuizScore());
        }
    }

    public void printQuizScoresAscending() {
        List<Integer> allScores = new ArrayList<>();
        for (Student student : students) {
            allScores.addAll(student.getQuizScores());
        }
        Collections.sort(allScores);
        System.out.println("All quiz scores in ascending order: " + allScores);
    }

    public void printPartTimeStudentNames() {
        System.out.println("Part-Time Students:");
        for (Student student : students) {
            if (student instanceof PartTimeStudent) {
                System.out.println(student.getName());
            }
        }
    }

    public void printFullTimeStudentExamScores() {
        System.out.println("Full-Time Students Exam Scores:");
        for (Student student : students) {
            if (student instanceof FullTimeStudent) {
                int[] examScores = ((FullTimeStudent) student).getExamScores();
                System.out.println(student.getName() + " exam scores: " + examScores[0] + ", " + examScores[1]);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Session session = new Session();

        for (int i = 1; i <= 10; i++) {
            PartTimeStudent ptStudent = new PartTimeStudent("Part-Time Student " + i);
            for (int j = 0; j < 15; j++) {
                ptStudent.addQuizScore(random.nextInt(101));
            }
            session.addStudent(ptStudent);
        }

        for (int i = 1; i <= 10; i++) {
            FullTimeStudent ftStudent = new FullTimeStudent("Full-Time Student " + i, random.nextInt(101), random.nextInt(101));
            for (int j = 0; j < 15; j++) {
                ftStudent.addQuizScore(random.nextInt(101));
            }
            session.addStudent(ftStudent);
        }

        session.calculateAverageQuizScores();
        session.printQuizScoresAscending();
        session.printPartTimeStudentNames();
        session.printFullTimeStudentExamScores();
    }
}