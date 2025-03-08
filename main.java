package porject;

import opencsv.FileReader;
import opencsv.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private void MarkAttendance(String regNo, int index) {
        List<String[]> CSVFILE = new ArrayList<>();
        try {
            File file = new File("student.csv");
            BufferedReader csvFile = new BufferedReader(new FileReader(file));
            String line;
            boolean isFound = false;

            while ((line = csvFile.readLine()) != null) {
                String word[] = line.split(",");
                if (word[3].equals(regNo)) {
                    isFound = true;
                    int atte = Integer.parseInt(word[index]);
                    word[index] = String.valueOf(atte + 1);
                }
                CSVFILE.add(word);
            }
            csvFile.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("student.csv"))) {
            writer.writeAll(CSVFILE);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void attendance() {
        Scanner sc = new Scanner(System.in);
        try (BufferedReader teacherFile = new BufferedReader(new FileReader("teacher.csv"))) {

            boolean access = false;
            String name;
            System.out.print("Enter teacher name: ");
            name = sc.nextLine();
            String code;
            System.out.print("Access code: ");
            code = sc.nextLine();

            String line;
            String subject = null;

            while ((line = teacherFile.readLine()) != null) {
                String LineAry[] = line.split(",");
                if (LineAry[0].equals(name) && LineAry[4].equals(code)) {
                    subject = LineAry[3];
                    access = true;
                    break;
                }
            }

            if (access) {
                BufferedReader studentFile = new BufferedReader(new FileReader("student.csv"));
                int ind = studentFile.readLine().indexOf(subject);
                String regNo;

                while (true) {
                    System.out.print("Enter registration number (-1 to stop): ");
                    regNo = sc.nextLine();
                    try {
                        int reg = Integer.parseInt(regNo);
                        if (reg < 0) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid registration number.");
                        continue;
                    }
                    MarkAttendance(regNo, ind);
                }
                studentFile.close();
            } else {
                System.out.println("Access Denied! Incorrect credentials.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main call = new Main();
        call.attendance();
    }
}
