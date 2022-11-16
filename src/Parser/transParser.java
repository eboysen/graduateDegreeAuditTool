package Parser;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class transParser {
    //for course parsing
    String courseName;
    String number;
    ArrayList<String> instructors = new ArrayList<>();
    String attempted;
    String earned;
    String points;
    //for external degree parsing
    String school;
    String degreeName;
    String dateFinished;
    //for semester parsing
    String year;
    String season;
    ArrayList<course> courses = new ArrayList<>();
    gpa term;
    gpa transfer;
    gpa combined;
    gpa cum;
    gpa transCum;
    gpa combinedCum;
    //for final transcript parsing
    String studentName;
    String studentId;
    ArrayList<externalDeg> exDegs = new ArrayList<>();
    ArrayList<semester> sems = new ArrayList<>();
    gpa totCumGpa;
    gpa totTransGpa;
    gpa totCombGpa;

    public String getDegreeName() {
        return degreeName;
    }

    public ArrayList<semester> getSems() {
        return sems;
    }

    //scanner
    File trans;

    public transParser() {}

    public void read(String fname) {
        trans = new File(fname);
        try {
            Scanner in = new Scanner(trans);
            String line;
            String[] lineArr;
            // lineArr = scanTill(in, "Name:", 0);
            // studentName = scanEnd(lineArr, 1, 0);
            // System.out.println(studentName);
            // lineArr = scanTill(in, "ID:", 1);
            line = checkPageEnd(in);
            lineArr = line.split(" ");
            studentId = lineArr[2];
            lineArr = scanTill(in, "External", 0);
            line = checkPageEnd(in);
            while (!line.equals("Beginning of Graduate Record")) {
                school = line;
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                dateFinished = lineArr[lineArr.length - 1];
                checkPageEnd(in);
                checkPageEnd(in);
                checkPageEnd(in);
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                degreeName = scanEnd(lineArr, 1, 0);
                checkPageEnd(in);
                line = checkPageEnd(in);
                exDegs.add(new externalDeg(school, degreeName, dateFinished));
            }
            while (!line.equals("Graduate Career Totals")) {
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                year = lineArr[0];
                season = lineArr[1];
                checkPageEnd(in);
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                courses = new ArrayList<>();
                while (!line.equals("Attempted Earned GPA Uts Points")) {
                    number = lineArr[0] + " " + lineArr[1];
                    int aL = lineArr.length;
                    points = lineArr[aL - 1];
                    if (Character.isLetter(lineArr[aL - 2].charAt(0))) {
                        earned = lineArr[aL - 3];
                        attempted = lineArr[aL - 4];
                        courseName = scanEnd(lineArr, 2, 4);
                    } else {
                        earned = lineArr[aL - 2];
                        attempted = lineArr[aL - 3];
                        courseName = scanEnd(lineArr, 2, 3);
                    }
                    line = checkPageEnd(in);
                    lineArr = line.split(" ");
                    instructors = new ArrayList<>();
                    instructors.add(scanEnd(lineArr, 1, 0));
                    line = checkPageEnd(in);
                    lineArr = line.split(" ");
                    while (Character.isLetter(lineArr[lineArr.length - 1].charAt(0)) && !line.equals("Attempted Earned GPA Uts Points")) {
                        instructors.add(line);
                        line = checkPageEnd(in);
                        lineArr = line.split(" ");
                    }
                    courses.add(new course(courseName, number, instructors, attempted, earned, points));
                }
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                term = new gpa("Term", lineArr[2], lineArr[5], lineArr[6], lineArr[7], lineArr[8]);
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                if (Character.isLetter(lineArr[3].charAt(0))) {
                    transfer = new gpa("Transfer Term", "0", lineArr[5], lineArr[6], lineArr[7], lineArr[8]);
                } else {
                    transfer = new gpa("Transfer Term", lineArr[3], lineArr[6], lineArr[7], lineArr[8], lineArr[9]);
                }
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                combined = new gpa("Combined", lineArr[2], lineArr[5], lineArr[6], lineArr[7], lineArr[8]);
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                cum = new gpa("Cum", lineArr[2], lineArr[5], lineArr[6], lineArr[7], lineArr[8]);
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                if (Character.isLetter(lineArr[3].charAt(0))) {
                    transCum = new gpa("Transfer Cum", "0", lineArr[5], lineArr[6], lineArr[7], lineArr[8]);
                } else {
                    transCum = new gpa("Transfer Cum", lineArr[3], lineArr[6], lineArr[7], lineArr[8], lineArr[9]);
                }
                line = checkPageEnd(in);
                lineArr = line.split(" ");
                combinedCum = new gpa("Combined Cum", lineArr[3], lineArr[6], lineArr[7], lineArr[8], lineArr[9]);
                line = checkPageEnd(in);
                sems.add(new semester(year, season, courses, term, transfer, combined, cum, transCum, combinedCum));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

    }

    public static String scanEnd(String[] lineArr, int startPos, int endPos) {
        String s = "";
        for (int i = startPos; i < lineArr.length - endPos; i++) {
            s = s + lineArr[i];
            if (i != lineArr.length - 1) {
                s = s + " ";
            }
        }
        return s;
    }

    public static String[] scanTill(Scanner scan, String word, int pos) {
        String line = scan.nextLine();
        String[] lineArr = line.split(" ");
        while (scan.hasNextLine()) {
            if (pos >= lineArr.length || !lineArr[pos].equals(word)) {
                line = scan.nextLine();
                lineArr = line.split(" ");
                //System.out.println(lineArr[pos]);
            } else {
                break;
            }
        }
        return lineArr;
    }

    public static String checkPageEnd(Scanner scan) {
        String line = scan.nextLine();
        String[] lineArr = line.split(" ");
        if ((lineArr.length >= 2 &&
                (Character.isDigit(lineArr[0].charAt(0)) && Character.isDigit(lineArr[1].charAt(0)))) ||
                line.equals("Unofficial Transcript - UT-Dallas") ||
                lineArr[0].equals("Name:")) {
            return checkPageEnd(scan);
        } else {
            return line;
        }
    }
}