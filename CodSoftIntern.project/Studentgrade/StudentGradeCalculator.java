package Studentgrade;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder report = new StringBuilder();

        System.out.println("Student Grade Calculator");

        //NAME
        System.out.print("Enter student's name: ");
        String studentName = scanner.nextLine();

        
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        scanner.nextLine(); 

        String[] subjectNames = new String[numSubjects];
        int[] marks = new int[numSubjects];
        int totalMarks = 0;

        // Input subject names and marks
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter name of Subject " + (i + 1) + ": ");
            subjectNames[i] = scanner.nextLine();

            while (true) {
                System.out.print("Enter marks for " + subjectNames[i] + " (out of 100): ");
                int mark = scanner.nextInt();
                if (mark >= 0 && mark <= 100) {
                    marks[i] = mark;
                    totalMarks += mark;
                    scanner.nextLine(); 
                    break;
                } else {
                    System.out.println("Invalid mark! Please enter a number between 0 and 100.");
                }
            }
        }

        // AVERAGE
        double averagePercentage = (double) totalMarks / numSubjects;

        // GRADE and REMARK
        String grade, remark;
        if (averagePercentage >= 95 ){
            grade = "A+";
            remark = "Outstanding";
        } else if (averagePercentage >= 85) {
            grade = "A";
            remark = "Excellent";
        } else if (averagePercentage >= 75) {
            grade = "B+";
            remark = "Very Good";
        } else if (averagePercentage >= 65) {
            grade = "B";
            remark = "Good";
        } else if (averagePercentage >= 60) {
            grade = "C";
            remark = "Needs improvment";
        } else if (averagePercentage >= 50) {
            grade = "D";
            remark = "Try Hard";
        } else {
            grade = "F";
            remark = "Failed";
        }

        // REPORT CARD
        report.append("Report Card for ").append(studentName).append("\n");
        report.append("--------------------------------------------------\n");
        report.append(String.format("%-25s %-10s\n", "Subject", "Marks"));
        report.append("--------------------------------------------------\n");
        for (int i = 0; i < numSubjects; i++) {
            report.append(String.format("%-25s %-10d\n", subjectNames[i], marks[i]));
        }
        report.append("--------------------------------------------------\n");
        report.append("Total Marks       : ").append(totalMarks).append(" / ").append(numSubjects * 100).append("\n");
        report.append(String.format("Average Percentage: %.2f%%\n", averagePercentage));
        report.append("Grade             : ").append(grade).append("\n");
        report.append("Remark            : ").append(remark).append("\n");

        
        System.out.println("\n" + report);

       
        try {
            FileWriter writer = new FileWriter(studentName + ".txt");
            writer.write(report.toString());
            writer.close();
            System.out.println("Report saved to 'ReportCard.txt'");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        scanner.close();
    }
}
