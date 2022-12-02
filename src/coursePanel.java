import Parser.course;

import javax.swing.*;
import java.awt.*;

public class coursePanel extends JPanel {
    BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
    JComboBox courseNumBox = new JComboBox<String>();
    JTextField utdSemField = new JTextField();
    JCheckBox transferBox = new JCheckBox();
    JComboBox gradeBox = new JComboBox<String>();

    public coursePanel() {


        courseNumBox.setPreferredSize(new Dimension(250, 25));
        utdSemField.setPreferredSize(new Dimension(250, 25));
        gradeBox.setPreferredSize(new Dimension(250, 25));
        utdSemField.setMaximumSize(new Dimension(25000, 25));

        courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {""}));
        gradeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"", "A+/A", "A-", "B+", "B", "B-", "C+", "C", "F"}));

        courseNumBox.setEditable(true);
        this.setLayout(layout);


        this.add(courseNumBox);
        this.add(Box.createHorizontalGlue());
        this.add(utdSemField);
        this.add(Box.createHorizontalGlue());
        this.add(Box.createRigidArea(new Dimension(40, 0)));
        this.add(transferBox);
        this.add(Box.createRigidArea(new Dimension(40, 0)));
        this.add(Box.createHorizontalGlue());
        this.add(gradeBox);
    }

    public void setCourseList(String[] s) {

    }

}
