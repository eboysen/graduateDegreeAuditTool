
import Parser.course;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;


import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


public class PdfGenerator {


    public static void generateDegreePlanPdf(String name, String id, String Sem, String Track, ArrayList<course> cores, ArrayList<course> electives) throws FileNotFoundException {
        //Document Setup
        PdfWriter writer = new PdfWriter((name + Track + " Degree Plan.pdf").replaceAll(" ", ""));
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);



        //Creating the title
        Paragraph p = new Paragraph("University of Texas at Dallas\n").setBold().setTextAlignment(TextAlignment.CENTER);
        p.add("Master of Computer Science\n\n");
        p.add(Track); //Insert Track Name Here
        //Creating name form
        Paragraph n = new Paragraph("Name: " + name + "\n");
        n.add("Student I.D. Number: " + id + "\n");
        n.add("Semester Admitted to Program: " + Sem);
        n.add("\t\tAnticipated Graduation: ");

        // Creating a table
        Table table = new Table(UnitValue.createPercentArray(new float[]{5, 5, 5, 5}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Creating Header

        //table.addCell("Name").setBold();
        table.addCell("Number").setBold();
        table.addCell("Semester").setBold();
        table.addCell("Transfer").setBold();
        table.addCell("Grade").setBold();


        // Adding Core Courses Label
        float [] pointColumnWidths2 = {1300F};
        Table table2 = new Table(pointColumnWidths2);
        table2.addCell("Core Courses").setBackgroundColor(ColorConstants.YELLOW);
        table2.setWidth(UnitValue.createPercentValue(100));

        // Adding Actual courses
        Table core_table = new Table(UnitValue.createPercentArray(new float[]{5, 5, 5, 5}));
        core_table.setWidth(UnitValue.createPercentValue(100));
        for (course c : cores){
            core_table.addCell(c.getNumber());
            //core_table.addCell("");
            core_table.addCell(c.getAttempted());
            core_table.addCell(c.getEarned());
            core_table.addCell(c.getPoints());
        }
        // Adding Elective Courses Label
        Table electivelabel = new Table(pointColumnWidths2);
        electivelabel.addCell("Elective Courses").setBackgroundColor(ColorConstants.YELLOW);
        electivelabel.setWidth(UnitValue.createPercentValue(100));

        //Adding Elective Courses
        Table electives_table = new Table(UnitValue.createPercentArray(new float[]{5, 5, 5, 5}));
        electives_table.setWidth(UnitValue.createPercentValue(100));
        for (course c : electives){
            electives_table.addCell(c.getNumber());
            //electives_table.addCell("");
            electives_table.addCell(c.getAttempted());
            electives_table.addCell(c.getEarned());
            electives_table.addCell(c.getPoints());
        }



        // Adding Document Elements
        doc.add(p);
        doc.add(n);
        doc.add(table);
        doc.add(table2);
        doc.add(core_table);
        doc.add(electivelabel);
        doc.add(electives_table);

        // Closing the document
        doc.close();
        System.out.println("Table created successfully..");
    }
    public static void generateAuditReportPdf(String info, String name, String Track, String Sem)
            throws FileNotFoundException{
        //Document Setup
        PdfWriter writer = new PdfWriter(name + " Audit Report.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        //Creating the title
        Paragraph p = new Paragraph("University of Texas at Dallas\n").setBold().setTextAlignment(TextAlignment.CENTER);
        p.add("Master of Computer Science\n\n");
        p.add(Track); //Insert Track Name Here
        //Creating name form
        Paragraph n = new Paragraph("Name: " + name + "\n");
        n.add("Semester Admitted to Program: " + Sem);

        //Adding the info dump text
        Paragraph i = new Paragraph(info).setFontSize(10);
        doc.add(p);
        doc.add(n);
        doc.add(i);

        // Closing the document
        doc.close();
        System.out.println("Report created successfully..");
    }
}
