import com.itextpdf.layout.properties.HorizontalAlignment;

import javax.swing.*;
import java.awt.*;

public class labelPanel extends JPanel {
    BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
    String grade;
    String courseNum;
    String semester;
    boolean transfer = false;
    JLabel courseNumLabel = new JLabel("Course Number");
    JLabel utdSemLabel = new JLabel("UTD Semester");
    JLabel transferLabel = new JLabel("Transfer");
    JLabel gradeLabel = new JLabel("Grade");

    public labelPanel() {
        courseNumLabel.setPreferredSize(new Dimension(250, 25));
        utdSemLabel.setPreferredSize(new Dimension(250, 25));
        gradeLabel.setPreferredSize(new Dimension(250, 25));
        transferLabel.setPreferredSize(new Dimension(101, 25));
        courseNumLabel.setMinimumSize(new Dimension(31, 25));
        utdSemLabel.setMinimumSize(new Dimension(5, 25));
        gradeLabel.setMinimumSize(new Dimension(31, 25));
        transferLabel.setMinimumSize(new Dimension(21, 25));
        utdSemLabel.setMaximumSize(new Dimension(25000, 25));
        gradeLabel.setMaximumSize(new Dimension(32767, 25));
        courseNumLabel.setMaximumSize(new Dimension(32767, 25));
        transferLabel.setMaximumSize(new Dimension(101, 25));
        transferLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setLayout(layout);


        this.add(courseNumLabel);
        this.add(Box.createHorizontalGlue());
        this.add(utdSemLabel);
        this.add(Box.createHorizontalGlue());
        this.add(transferLabel);
        this.add(Box.createHorizontalGlue());
        this.add(gradeLabel);
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

}
