import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        
        System.out.println("=======================================");
        System.out.println("       Student Grade Tracker           ");
        System.out.println("=======================================");

        while (true) {
            System.out.print("Enter a student's grade (or type -1 to finish): ");
            try {
                double grade = Double.parseDouble(scanner.nextLine().trim());
                if (grade == -1) {
                    break;
                } else if (grade < 0 || grade > 100) {
                    System.out.println("Invalid grade! Please enter a value between 0 and 100.");
                } else {
                    grades.add(grade);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }

        if (grades.isEmpty()) {
            System.out.println("\nNo grades were entered. Exiting system.");
        } else {
            double total = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);

            for (double grade : grades) {
                total += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
            }

            double average = total / grades.size();

            System.out.println("\n=======================================");
            System.out.println("           Summary Report              ");
            System.out.println("=======================================");
            System.out.println("Total Students : " + grades.size());
            System.out.printf("Average Grade  : %.2f\n", average);
            System.out.printf("Highest Grade  : %.2f\n", highest);
            System.out.printf("Lowest Grade   : %.2f\n", lowest);
            System.out.println("=======================================");
        }
        
        scanner.close();
    }
}
