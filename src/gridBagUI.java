import Parser.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;

public class gridBagUI {
    static JFrame f;
    private static void addComponentsToPane(    Container pane, JFrame fra) {
        f = fra;
        coursePanel p;
        ArrayList<coursePanel> cores = new ArrayList<>();
        ArrayList<coursePanel> electives = new ArrayList<>();
        ArrayList<coursePanel> prereqs = new ArrayList<>();
        JTextField name = new JTextField();
        JTextField id = new JTextField();
        JTextField curSem = new JTextField();
        JTextField semAdm = new JTextField();
        JButton importButton = new JButton("Import");
        JButton generateReportButton = new JButton("Generate Audit Report");
        JButton generateDegreePlanButton = new JButton("Generate Degree Plan");
        JComboBox trackBox = new JComboBox();
        trackBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"", "Cyber Security", "Data Science", "Intelligent Systems", "Interactive Computing", "Networks and Telecommunications", "Systems", "Traditional"}));

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;

        pane.add(new JLabel("Student Name:"), c);
        c.gridx = 1;
        pane.add(name, c);
        c.gridx = 2;
        pane.add(new JLabel("Student ID Number:"), c);
        c.gridx = 3;
        pane.add(id, c);

        c.gridy = 1;
        c.gridx = 0;
        pane.add(new JLabel("Current Semester:"), c);
        c.gridx = 1;
        pane.add(curSem, c);
        c.gridx = 2;
        pane.add(new JLabel("Semester Admitted:"), c);
        c.gridx = 3;
        pane.add(semAdm, c);
        c.gridx = 4;
        pane.add(new JLabel("Track:"), c);
        c.gridx = 5;
        pane.add(trackBox, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 6;
        pane.add(new labelPanel(), c);

        c.gridy = 3;
        pane.add(new JLabel("Core Courses"), c);
        for (int i = 4; i < 9; i++) {
            c.gridy = i;
            p = new coursePanel();
            cores.add(p);
            pane.add(p, c);
        }

        c.gridy = 9;
        pane.add(new JLabel("Elective Courses"), c);

        for (int i = 10; i < 16; i++) {
            c.gridy = i;
            p = new coursePanel();
            electives.add(p);
            pane.add(p, c);
        }

        c.gridy = 16;
        pane.add(new JLabel("Prerequisites"), c);

        for (int i = 17; i < 24; i++) {
            c.gridy = i;
            p = new coursePanel();
            prereqs.add(p);
            pane.add(p, c);
        }

        c.gridy = 24;
        c.gridwidth = 1;
        pane.add(importButton, c);
        c.gridx = 1;
        pane.add(generateReportButton, c);
        c.gridx = 2;
        pane.add(generateDegreePlanButton, c);

        trackBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Degree d;
                int row = 0;
                try {
                    d = new Degree(trackBox.getSelectedItem().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Requirement r : d.getRequirements()) {
                    if (r.getClass().toString().equals("class MultipleRequirement")) {
                        MultipleRequirement m = (MultipleRequirement) r;
                        while (row < 5) {
                            cores.get(row).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(m.getOptions().toArray(new String[0])));
                            row++;
                        }
                    } else if (r.getClass().toString().equals("class CourseRequirement")) {
                        CourseRequirement c = (CourseRequirement) r;
                        cores.get(row).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {c.toString()}));
                        row++;
                    }
                }
            }
        });

        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList<Double> grades = new ArrayList(Arrays.asList(4.000, 3.670, 3.330, 3.000, 2.670, 2.330, 2.000, 0.000));
                ArrayList<String> letters = new ArrayList(Arrays.asList("A+/A", "A-", "B+", "B", "B-", "C+", "C", "F"));

                String fileName = importActionPerformed(evt);
                Degree d;
                try {
                    d = new Degree(trackBox.getSelectedItem().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                transParser p = new transParser();
                transcript t = p.read(fileName);
                System.out.println(p.getDegreeName());
                HashMap<String, course> h = new HashMap();
                for (semester s : p.getSems()) {
                    for (course c : s.getCourses()) {
                        h.put(c.getNumber(), c);
                    }
                }

                for (transSem s : p.getTransSems()) {
                    for (course c : s.getCourses()) {
                        h.put(c.getNumber(), c);
                    }
                }

                for (String k : h.keySet()) {
                    System.out.println(k + " " + h.get(k).getName());
                }

                d.validateDegreePlan(h,new HashMap<String, String>());

                int coreInd = 0;
                int electInd = 0;


                for (semester s : p.getSems()) {
                    for (course c : s.getCourses()) {
                        if (d.transcriptCores.containsKey(c.getNumber())) {
                            cores.get(coreInd).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {c.getNumber()}));
                            cores.get(coreInd).utdSemField.setText(s.getYear() + s.getSeason());
                            cores.get(coreInd).gradeBox.setSelectedIndex(grades.indexOf(Double.parseDouble(c.getPoints()) / Double.parseDouble(c.getAttempted())) + 1);
                            //cores.get(coreInd).
                            coreInd++;
                        }

                        if (d.finalElectives.containsKey(c.getNumber())) {
                            electives.get(electInd).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {c.getNumber()}));
                            electives.get(electInd).utdSemField.setText(s.getYear() + s.getSeason());
                            electives.get(electInd).gradeBox.setSelectedIndex(grades.indexOf(Double.parseDouble(c.getPoints()) / Double.parseDouble(c.getAttempted())) + 1);
                            electInd++;
                        }

                    }
                }

                for (transSem s : p.getTransSems()) {
                    for (course c : s.getCourses()) {
                        if (d.transcriptCores.containsKey(c.getNumber())) {
                            cores.get(coreInd).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {c.getNumber()}));
                            cores.get(coreInd).utdSemField.setText(s.getYear() + s.getSeason());
                            cores.get(coreInd).transferBox.setSelected(true);
                            cores.get(coreInd).gradeBox.setSelectedIndex(grades.indexOf(Double.parseDouble(c.getPoints()) / Double.parseDouble(c.getAttempted())) + 1);
                            coreInd++;
                        }

                        if (d.finalElectives.containsKey(c.getNumber())) {
                            electives.get(electInd).courseNumBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {c.getNumber()}));
                            electives.get(electInd).utdSemField.setText(s.getYear() + s.getSeason());
                            electives.get(electInd).transferBox.setSelected(true);
                            electives.get(electInd).gradeBox.setSelectedIndex(grades.indexOf(Double.parseDouble(c.getPoints()) / Double.parseDouble(c.getAttempted())) + 1);
                            electInd++;
                        }
                    }
                }

                name.setText(t.name);
                id.setText(t.studentId);
                curSem.setText(p.getSems().get(p.getSems().size() - 1).getYear() + p.getSems().get(p.getSems().size() - 1).getSeason());
            }
        });



        generateReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Degree d;
                HashMap<String, course> transcript = new HashMap<>();
                HashMap<String, Double> grades = new HashMap<>();
                grades.put("A+/A", 4.0);
                grades.put("A-", 3.670);
                grades.put("B+", 3.330);
                grades.put("B", 3.000);
                grades.put("B-", 2.670);
                grades.put("C+", 2.330);
                grades.put("C", 2.000);
                grades.put("F", 0.000);

                try {
                    d = new Degree(trackBox.getSelectedItem().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                for (coursePanel p : cores) {
                    String num = p.courseNumBox.getSelectedItem().toString();
                    System.out.println(num);
                    if (p.utdSemField.getText() != curSem.getText() && !p.gradeBox.getSelectedItem().toString().equals("")) {
                        transcript.put(num, new course("", num, new ArrayList<String>(), num.substring(num.length() - 3, num.length() - 2), num.substring(num.length() - 3, num.length() - 2), Double.toString(Double.parseDouble(num.substring(num.length() - 3, num.length() - 2)) * grades.get(p.gradeBox.getSelectedItem().toString()))));
                    }
                }

                for (coursePanel p : electives) {
                    String num = p.courseNumBox.getSelectedItem().toString();
                    System.out.println(num);
                    if (p.utdSemField.getText() != curSem.getText() && !p.gradeBox.getSelectedItem().toString().equals("")) {
                        transcript.put(num, new course("", num, new ArrayList<String>(), num.substring(num.length() - 3, num.length() - 2), num.substring(num.length() - 3, num.length() - 2), Double.toString(Double.parseDouble(num.substring(num.length() - 3, num.length() - 2)) * grades.get(p.gradeBox.getSelectedItem().toString()))));
                    }
                }

                for (coursePanel p : prereqs) {
                    String num = p.courseNumBox.getSelectedItem().toString();
                    System.out.println(num);
                    if (p.utdSemField.getText() != curSem.getText() && !p.gradeBox.getSelectedItem().toString().equals("")) {
                        transcript.put(num, new course("", num, new ArrayList<String>(), num.substring(num.length() - 3, num.length() - 2), num.substring(num.length() - 3, num.length() - 2), Double.toString(Double.parseDouble(num.substring(num.length() - 3, num.length() - 2)) * grades.get(p.gradeBox.getSelectedItem().toString()))));
                    }
                }

                try {
                    PdfGenerator.generateAuditReportPdf(d.validateDegreePlan(transcript, new HashMap<String, String>()), name.getText(), trackBox.getSelectedItem().toString(), semAdm.getText());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        generateDegreePlanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList<course> coreList = new ArrayList<>();
                ArrayList<course> electiveList = new ArrayList<>();

                for (coursePanel c : cores) {
                    coreList.add(new course("", c.courseNumBox.getSelectedItem().toString(), new ArrayList<String>(), c.utdSemField.getText(), Boolean.toString(c.transferBox.isSelected()), c.gradeBox.getSelectedItem().toString()));
                }
                for (coursePanel c : electives) {
                    electiveList.add(new course("", c.courseNumBox.getSelectedItem().toString(), new ArrayList<String>(), c.utdSemField.getText(), Boolean.toString(c.transferBox.isSelected()), c.gradeBox.getSelectedItem().toString()));
                }
                try {
                    PdfGenerator.generateDegreePlanPdf(name.getText(), id.getText(), semAdm.getText(), trackBox.getSelectedItem().toString(), coreList, electiveList);
                } catch(FileNotFoundException e) {
                    System.out.println("file not found");
                }

            }
        });


    }

    private static String importActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Import");
        JFileChooser loadFile= new JFileChooser();
        loadFile.setApproveButtonText("Select File");
        loadFile.setAcceptAllFileFilterUsed(false);
        if (loadFile.showOpenDialog(f) ==  JFileChooser.APPROVE_OPTION) {
            return loadFile.getSelectedFile().getAbsolutePath();
        }
        loadFile.setVisible(true);
        return "";
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane(), frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
