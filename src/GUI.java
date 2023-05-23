
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class GUI implements ActionListener {

    private static final int FRAME_SIZE = 800;
    private final JFrame welcomeFrame = new JFrame();
    private final JFrame editFrame = new JFrame();
    private JButton makeNewPacking;
    private JButton exit;

    private JSpinner addSpinnerX;
    private JSpinner addSpinnerY;
    private JSpinner addSpinnerZ;
    private JSpinner addSpinnerQ;
    private InputTable pt;
    private JTextField nameInput;

    private long id = 0;
    private JCheckBox flip;
    private JSpinner groupSpinner;
    private JCheckBox conModel;
    private JSpinner addConSpinnerX;
    private JSpinner addConSpinnerY;
    private JSpinner addConSpinnerZ;
    private JTextField conNameInput;
    private JSpinner addConSpinnerQ;
    private InputTable containersTable;
    private boolean hasModel = false;
    private Solution solution;
    private JComboBox<Integer> constraintLevel;


    public GUI() {
        this.setHomeFrame();
        this.setEditFrame();

        // Show the JFrame
        this.welcomeFrame.setVisible(true);
        this.editFrame.setVisible(false);

    }



    private void setHomeFrame() {
        this.welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.welcomeFrame.setSize(2 * FRAME_SIZE,FRAME_SIZE);
        this.welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.welcomeFrame.setMinimumSize(new Dimension(2 * FRAME_SIZE, FRAME_SIZE));

        this.welcomeFrame.setIconImage(new ImageIcon(getClass().getResource("res/icons/logo.png")).getImage());
        this.welcomeFrame.setTitle("Lambeau");
        this.welcomeFrame.setLayout(new BorderLayout());
        this.welcomeFrame.setBackground(Color.WHITE);
        JPanel out = new JPanel();
        out.setBackground(Color.WHITE);
        JPanel row1 = new JPanel();
        row1.setBackground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.WHITE);
        out.setLayout(new BoxLayout(out, BoxLayout.Y_AXIS));

        ImageIcon ii = new ImageIcon(getClass().getResource("res/icons/logo.png"));
        JLabel iLabel = new JLabel();
        iLabel.setIcon(ii);
        row1.add(iLabel, BorderLayout.CENTER);

        JButton make = new JButton("New");
        make.setFocusPainted(false);
        make.setIcon(new ImageIcon(getClass().getResource("res/icons/add.png")));
        make.addActionListener(this);
        this.makeNewPacking = make;

        JButton load = new JButton("Load");
        load.setFocusPainted(false);
        load.setIcon(new ImageIcon(getClass().getResource("res/icons/load.png")));

        JButton exit = new JButton("Exit");
        exit.setFocusPainted(false);
        exit.setIcon(new ImageIcon(getClass().getResource("res/icons/exit.png")));
        exit.addActionListener(this);
        this.exit = exit;


        row2.add(make);
        row2.add(new Label("\t"));
        row2.add(load);
        row2.add(new Label("\t"));
        row2.add(exit);
        out.add(row1);
        out.add(row2);
        this.welcomeFrame.add(out);
    }

    private void setEditFrame() {
        this.editFrame.setLayout(new BoxLayout(this.editFrame.getContentPane(), BoxLayout.Y_AXIS));
        this.editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.editFrame.setSize(2 * FRAME_SIZE, FRAME_SIZE);
        this.editFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.editFrame.setMinimumSize(new Dimension(2 * FRAME_SIZE, FRAME_SIZE));
        this.editFrame.setIconImage(new ImageIcon(getClass().getResource("res/icons/logo.png")).getImage());
        this.editFrame.setTitle("Lambeau");

        JPanel visualWorkArea = new JPanel();
        visualWorkArea.setLayout(new BoxLayout(visualWorkArea, BoxLayout.X_AXIS));

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBackground(Color.white);
        JPanel row1 = new JPanel();
        row1.setBackground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.WHITE);
        JPanel row3 = new JPanel();
        row3.setBackground(Color.WHITE);

        setContainer(row2);
        setAdd(row3);

        JButton reset = new JButton("Reset");
        reset.setIcon(new ImageIcon(getClass().getResource("res/icons/reset.png")));
        reset.setFocusPainted(false);

        reset.addActionListener(e -> {
           this.pt.removeAllRows();
           this.containersTable.removeAllRows();
        });

        JButton show3d = new JButton("Show 3D");
        show3d.setFocusPainted(false);
        show3d.setEnabled(false);

        show3d.setIcon(new ImageIcon(getClass().getResource("res/icons/3d-model.png")));

        JButton instructions = new JButton("Show Packing Instructions");
        instructions.setFocusPainted(false);
        instructions.setEnabled(false);

        instructions.setIcon(new ImageIcon(getClass().getResource("res/icons/instruction.png")));

        JButton pack = new JButton("Pack");
        pack.setFocusPainted(false);
        pack.addActionListener(e -> {
            int constraint = this.constraintLevel.getSelectedIndex();

            LinkedList<Bin> bins = new LinkedList<>();
            LinkedList<TableRow> consRows = this.containersTable.getRows();
            LinkedList<Package> packs = new LinkedList<>();
            LinkedList<TableRow> packsRows = this.pt.getRows();
            for (TableRow tr : consRows) {
                ContainerTableRow ctr = (ContainerTableRow)tr; 
                bins.addAll(ctr.toBin());
            }

            for (TableRow tr : packsRows) {
                PackageTableRow ptr = (PackageTableRow)tr;
                packs.add(ptr.toPack());
            }
            if(bins.size() > 0 && packs.size() > 0) {
                PackingManager pm = new PackingManager(bins, packs, constraint);
                this.solution = pm.findSolution();
                System.out.println(this.solution);
                if (this.solution != null) {
                    show3d.setEnabled(true);
                    instructions.setEnabled(true);
                }
            }
        });

        pack.setIcon(new ImageIcon(getClass().getResource("res/icons/pack.png")));

        JButton save = new JButton("Save");

        save.setIcon(new ImageIcon(getClass().getResource("res/icons/save.png")));
        save.setFocusPainted(false);
        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this.editFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                File f2 = new File(fileToSave.getAbsolutePath() + "-instructions.txt");
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(f2));
                    writer.write(this.solution.toString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });

        row1.add(reset);
        row1.add(save);
        row1.add(new JLabel("   "));
        row1.add(show3d);
        row1.add(instructions);
        row1.add(new JLabel("   Constraint Level: "));
        Integer[] options = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        this.constraintLevel = new JComboBox<>(options);
        row1.add(this.constraintLevel);

        row1.add(pack);

        controls.add(row1);
        controls.add(row2);
        controls.add(row3);

        // Create JScrollPane to add scroll functionality
        InputTable pt = new InputTable("Packages",
                PackageTableRow.NAMES(),
                PackageTableRow.CLASSES());
        this.pt = pt;
        JScrollPane scroller1 = pt.getScroller();
        scroller1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setVisible(true);
        scroller1.setPreferredSize(new Dimension(400,400));


        InputTable containers = new InputTable("Containers",
                ContainerTableRow.NAMES(),
                ContainerTableRow.CLASSES());
        this.containersTable = containers;
        JScrollPane scroller2 = containers.getScroller();
        scroller2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setVisible(true);
        scroller2.setPreferredSize(new Dimension(400,400));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroller1, scroller2);

        splitPane.setDividerLocation(1600);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0.2);

        visualWorkArea.add(splitPane);


        this.editFrame.add(controls);
        this.editFrame.add(visualWorkArea);

    }



    private void setContainer(JPanel jp) {
        JTextField textField = new JTextField(18);
        jp.add(new Label("Name:"));
        jp.add(textField);
        this.conNameInput = textField;

        SpinnerModel addModel1 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel2 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel3 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel4 = new SpinnerNumberModel(1.0, 1, 10000, 1);

        this.addConSpinnerX = new JSpinner(addModel1);
        this.addConSpinnerY = new JSpinner(addModel2);
        this.addConSpinnerZ = new JSpinner(addModel3);
        this.addConSpinnerQ = new JSpinner(addModel4);
        this.addConSpinnerQ.setEnabled(false);



        jp.add(new Label("  Width:"));
        jp.add(this.addConSpinnerX);
        jp.add(new Label("  Depth:"));
        jp.add(this.addConSpinnerY);
        jp.add(new Label("  Height:"));
        jp.add(this.addConSpinnerZ);
        jp.add(new Label("  Quantity:"));
        jp.add(this.addConSpinnerQ);


        jp.add(new Label("  Set As Container Model"));
        JCheckBox jcb1 = new JCheckBox("", true);
        jcb1.addActionListener(e -> this.addConSpinnerQ.setEnabled(!jcb1.isSelected()));
        jcb1.setBackground(Color.WHITE);
        this.conModel = jcb1;
        jp.add(jcb1);


        JButton addContainer = new JButton("Add Container");


        addContainer.addActionListener(e -> addContainer());
        addContainer.setFocusPainted(false);
        addContainer.setIcon(new ImageIcon(getClass().getResource("res/icons/container.png")));
        jp.add(addContainer);

        jp.add(new JLabel(" "));


        JButton setContainerModel = new JButton("Remove Container");
        setContainerModel.setIcon(new ImageIcon(getClass().getResource("res/icons/rubbish-bin.png")));

        setContainerModel.addActionListener(e -> {
            this.containersTable.remove();
        });
        setContainerModel.setFocusPainted(false);
        jp.add(setContainerModel);


        jp.add(new Label("  "));
    }

    private void addContainer() {
        if (this.hasModel) {
            this.containersTable.removeAllRows();
            this.hasModel = false;
        }
        String name = this.conNameInput.getText();
        if(name.equals("")) {
            name = "Container";
        }
        String w = this.addConSpinnerX.getValue().toString();
        String d = this.addConSpinnerY.getValue().toString();
        String h = this.addConSpinnerZ.getValue().toString();
        double w1 = Double.parseDouble(w);
        double d1 = Double.parseDouble(d);
        double h1 = Double.parseDouble(h);
        if (!this.conModel.isSelected()) {
            String q = this.addConSpinnerQ.getValue().toString();
            ContainerTableRow ctr = new ContainerTableRow(name,
                    w1,
                    d1,
                    h1,
                    Double.parseDouble(q));
            this.containersTable.add(ctr);
        } else {
            this.hasModel = true;
            this.containersTable.removeAllRows();
            ContainerTableRow ctr = new ContainerTableRow(name,
                    w1,
                    d1,
                    h1,
                    Double.POSITIVE_INFINITY);
            this.containersTable.add(ctr);
        }
    }

    private void setAdd(JPanel jp) {
        JTextField textField = new JTextField(18);
        jp.add(new Label("Name:"));
        jp.add(textField);
        this.nameInput = textField;

        SpinnerModel addModel1 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel2 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel3 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel4 = new SpinnerNumberModel(1.0, 1, 10000, 1);

        JSpinner as1 = new JSpinner(addModel1);
        JSpinner as2 = new JSpinner(addModel2);
        JSpinner as3 = new JSpinner(addModel3);
        JSpinner as4 = new JSpinner(addModel4);

        jp.add(new Label("  Width:"));
        jp.add(as1);
        jp.add(new Label("  Depth:"));
        jp.add(as2);

        jp.add(new Label("  Height:"));
        jp.add(as3);
        jp.add(new Label("  Quantity:"));
        jp.add(as4);

        this.addSpinnerX = as1;
        this.addSpinnerY = as2;
        this.addSpinnerZ = as3;
        this.addSpinnerQ = as4;
        jp.add(new Label("  Do Not Flip"));
        JCheckBox jcb1 = new JCheckBox("", false);
        jcb1.setBackground(Color.WHITE);
        this.flip = jcb1;
        jp.add(jcb1);

        JButton add = new JButton("Add Package");

        SpinnerModel addModel5 = new SpinnerNumberModel(0, 0, 10000, 1);
        JSpinner groupSpinner = new JSpinner(addModel5);
        jp.add(new Label("  Delivery Group:"));
        jp.add(groupSpinner);
        this.groupSpinner = groupSpinner;
        jp.add(new Label("  "));

        add.addActionListener(e -> addBox());
        add.setFocusPainted(false);
        add.setIcon(new ImageIcon(getClass().getResource("res/icons/addpackage.png")));
        jp.add(add);
        jp.add(new Label("  "));

        JButton r = new JButton("Remove Package");
        r.addActionListener(e -> {
            this.pt.remove();
        });
        r.setFocusPainted(false);
        r.setIcon(new ImageIcon(getClass().getResource("res/icons/removepackage.png")));

        jp.add(r);

    }

    private static void pause(double seconds)
    {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == this.makeNewPacking) {
            this.editFrame.setVisible(true);
            pause(0.5);
            this.welcomeFrame.setVisible(false);

        } else if (source == this.exit) {
            System.exit(1);
        }
    }


    private void addBox() {
        double q = Double.parseDouble(this.addSpinnerQ.getValue().toString());
        String name = this.nameInput.getText();

        String x = this.addSpinnerX.getValue().toString();
        String y = this.addSpinnerY.getValue().toString();
        String z = this.addSpinnerZ.getValue().toString();

        boolean f = this.flip.isSelected();

        String g = this.groupSpinner.getValue().toString();
        for (int i = 0; i < q; i++) {
            String n = name;
            if (name.equals("")) {
                n = String.valueOf(this.id);
            }
            PackageTableRow ptr = new PackageTableRow(n,
                    this.id++,
                    Double.parseDouble(x),
                    Double.parseDouble(y),
                    Double.parseDouble(z),
                    f,
                    Double.parseDouble(g));
            this.pt.add(ptr);
        }
    }


}