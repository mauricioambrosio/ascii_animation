//AsciiAnimBB.java
//Compile this file to run the project
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;


public class AsciiAnimBB extends JFrame implements ActionListener {
    public static final long serialVersionUID = 1L;

    AsciiCanvasBB cnvAnim = new AsciiCanvasBB();
    JButton btnNew = new JButton("new");//button that cleans everything and starts at the head
    JButton btnPrev = new JButton("prev");
    JButton btnNext = new JButton("next");

    JButton btnSaveLoad = new JButton("save/load");//button that opens new object of nested class JFrame AnimeSaveLoadBB
    JButton btnAnim = new JButton("animate");
    JButton btnIns = new JButton("insert");//insert button inserts node/frame behind the current node/frame
    JButton btnDel = new JButton("delete");//delete button deletes current node/frame

    JPanel pnlSouth = new JPanel();
    Timer timer = new Timer(100, this);

    boolean animating = false;

    public AsciiAnimBB() {
        this.setTitle("ASCII Animation Editor");//adds this title
        this.setUpGUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);//frame starts at the middle of the screen
    } // end constructor

    public void setUpGUI() {
        //like it says, set up GUI
        Container pnlMain = this.getContentPane();

        pnlSouth.setLayout(new FlowLayout());
        pnlMain.add(cnvAnim, BorderLayout.CENTER);
        pnlMain.add(pnlSouth, BorderLayout.SOUTH);

        pnlSouth.add(btnNew);
        pnlSouth.add(btnPrev);
        pnlSouth.add(btnNext);
        pnlSouth.add(btnAnim);
        pnlSouth.add(btnSaveLoad);
        pnlSouth.add(btnIns);
        pnlSouth.add(btnDel);

        //add action listeners
        btnNew.addActionListener(this);
        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnAnim.addActionListener(this);
        btnSaveLoad.addActionListener(this);
        btnIns.addActionListener(this);
        btnDel.addActionListener(this);

    } // end setUpGUI

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPrev) {
            cnvAnim.prevFrame();
        } else if (e.getSource() == btnNext) {
            cnvAnim.nextFrame();
        } else if (e.getSource() == btnNew) {//clears all the nodes/frames and put current to head
            cnvAnim.setHead(new AFrameBB());
            cnvAnim.setCurrent(cnvAnim.getHead());
            cnvAnim.setText(cnvAnim.getCurrent().getContent());
        } else if (e.getSource() == btnAnim) {
            //change the animation status
            if (timer.isRunning()) {
                btnAnim.setText("animate");
                timer.stop();
                this.animating = false;
            } else {
                cnvAnim.getCurrent().setContent(cnvAnim.getText());
                if (cnvAnim.getCurrent() != cnvAnim.getHead()) {
                    cnvAnim.getCurrent().getPrevFrame().setNextFrame(cnvAnim.getCurrent());
                }
                btnAnim.setText("stop");
                timer.start();
                this.animating = true;
            } // end if
        }

        else if (e.getSource() == btnSaveLoad) {//opens AnimSaveLoadBB frame
            this.new AnimSaveLoadBB();
        } else if (e.getSource() == btnIns) {
            cnvAnim.insert();
        } else if (e.getSource() == btnDel) {
            cnvAnim.delete();
        } else if (this.animating == true) {
            cnvAnim.anim();
        } else {
            System.out.println("action not defined");
        } // end if
    } // end actionPerformed

    public static void main(String[] args) {
        AsciiAnimBB mainProgram = new AsciiAnimBB();

    } // end main


    public class AnimSaveLoadBB extends JFrame implements ActionListener {//nested class AnimSaveLoadBB
        public static final long serialVersionUID = 1L;

        JComboBox<String> comboBox = new JComboBox<>();
        JButton btnSave2 = new JButton("save");
        JButton btnLoad2 = new JButton("load");
        JButton btnDel2 = new JButton("delete");
        JLabel lblInstruction = new JLabel("write directly on combo box to save to a new file");
        JPanel pnlCenter = new JPanel();
        JPanel pnlNorth = new JPanel();
        JPanel pnlSouth = new JPanel();


        String[] items;//items that will go to comboBox

        FilenameFilter fileFilter = new FilenameFilter() {//fileFilter files that end with .ser which are the saved animations
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".ser");
            }
        };

        public AnimSaveLoadBB() {
            File saves = new File(".");//saves gets working directory
            items = saves.list(fileFilter);//items gets list of files in working directory that end with .ser
            comboBox = new JComboBox<>(items);
            this.setTitle("Save/Load");
            this.setUpGUI();
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setVisible(true);
            this.setSize(300, 140);
            this.setLocationRelativeTo(null);
        }

        public void setUpGUI() {//like it says set up GUI
            Container pnlMain = this.getContentPane();
            comboBox.setEditable(true);
            pnlNorth.setLayout(new FlowLayout());
            pnlSouth.setLayout(new FlowLayout());
            pnlCenter.setLayout(new FlowLayout());
            pnlMain.add(pnlNorth, BorderLayout.NORTH);
            pnlMain.add(pnlSouth, BorderLayout.SOUTH);
            pnlMain.add(pnlCenter, BorderLayout.CENTER);
            pnlNorth.add(lblInstruction);
            pnlCenter.add(comboBox);
            pnlSouth.add(btnSave2);
            pnlSouth.add(btnLoad2);
            pnlSouth.add(btnDel2);

            btnSave2.addActionListener(this);
            btnLoad2.addActionListener(this);
            btnDel2.addActionListener(this);


        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSave2) {//button save is clicked
                String nameToSaveOnChecker = (String) comboBox.getSelectedItem();
                DefaultComboBoxModel<String> comboBoxModel=new DefaultComboBoxModel<>(items);

                if(comboBoxModel.getIndexOf(nameToSaveOnChecker)==-1){//if file to save on does not exist yet
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to save your progress on "+
                            nameToSaveOnChecker+"?", "Warning", dialogButton);
                    if (dialogResult == JOptionPane.NO_OPTION||dialogResult==JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                }else if(comboBoxModel.getIndexOf(nameToSaveOnChecker)!=-1){//if file to save on already exists
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, nameToSaveOnChecker+" already contains an animation, \nAre you sure you want to overwrite your current progress on "+
                            nameToSaveOnChecker+"?", "Warning", dialogButton);
                    if (dialogResult == JOptionPane.NO_OPTION||dialogResult==JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(this, "To save to a new file, make sure the name of the file does not already exist in the list",
                                "Inheritance Animation",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                if (!nameToSaveOnChecker.endsWith(".ser")) {//if file to save on does not end with .ser, add .ser
                    nameToSaveOnChecker = nameToSaveOnChecker + ".ser";
                }
                try (FileOutputStream toSaveOn = new FileOutputStream(nameToSaveOnChecker);
                     ObjectOutputStream objectOutput = new ObjectOutputStream(toSaveOn);) {

                    objectOutput.writeObject(cnvAnim.getHead());//save the head

                   JOptionPane.showMessageDialog(this, "Animation saved successfully",
                            "Inheritance Animation",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();//close save/load frame

                } catch (FileNotFoundException i) {
                    JOptionPane.showMessageDialog(this, i.getMessage(),
                            "Inheritance Animation",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException i) {
                    JOptionPane.showMessageDialog(this, i.getMessage(),
                            "Inheritance Animation",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == btnLoad2) {//button load is clicked
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "If you load an animation without saving your current one first \nall your progress will be lost, do you want to continue?", "Warning", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {

                    try (FileInputStream fileInput = new FileInputStream((String) comboBox.getSelectedItem());
                         ObjectInputStream objectInput = new ObjectInputStream(fileInput);) {

                        cnvAnim.setHead((AFrameBB) objectInput.readObject());
                        cnvAnim.setCurrent(cnvAnim.getHead());
                        cnvAnim.setText(cnvAnim.getCurrent().getContent());

                        JOptionPane.showMessageDialog(this, "Animation loaded successfully",
                                "Inheritance Animation",
                                JOptionPane.INFORMATION_MESSAGE);

                        this.dispose();//close save/load frame
                    } catch (FileNotFoundException i) {
                        JOptionPane.showMessageDialog(this, i.getMessage(),
                                "Inheritance Animation",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (IOException i) {
                        JOptionPane.showMessageDialog(this, i.getMessage(),
                                "Inheritance Animation",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException i) {
                        JOptionPane.showMessageDialog(this, i.getMessage(),
                                "Inheritance Animation",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == btnDel2) {//button delete is clicked
                String nameOfToBeDeleted = ((String) comboBox.getSelectedItem());
                //check if file to be deleted exists
                File saves = new File(".");
                String[] items = saves.list(fileFilter);
                LinkedList<String> itemsLinkedList = new LinkedList<>(Arrays.asList(items));
                if (!itemsLinkedList.contains(nameOfToBeDeleted)) {//if file to be deleted does not exist
                    JOptionPane.showMessageDialog(this, "Animation "+nameOfToBeDeleted+" does not exist",
                            "Inheritance Animation",
                            JOptionPane.ERROR_MESSAGE);

                }
                else{//if file to be deleted exists

                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this animation?", "Warning", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        File toBeDeleted = new File(nameOfToBeDeleted);
                        toBeDeleted.delete();
                        items = saves.list(fileFilter);
                        comboBox = new JComboBox<>(items);

                        JOptionPane.showMessageDialog(this, "Animation deleted successfully",
                                "Inheritance Animation",
                                JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();//close save/load frame
                    }
                }
            }else {
                System.out.println("action not defined");
            }
        }
    }
}
 // end class def

