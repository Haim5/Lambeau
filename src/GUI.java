import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.net.URL;

/**
 * GUI class.
 */
public class GUI {

    private static final int FRAME_SIZE = 800;

    // JFrames.
    private final JFrame welcomeFrame = new JFrame("Lambeau");
    private final JFrame editFrame = new JFrame("Lambeau - Editor");
    private final JFrame instructionsFrame = new JFrame("Lambeau - Instructions");

    // JSpinners.
    private JSpinner groupSpinner;
    private JSpinner addConSpinnerX;
    private JSpinner addConSpinnerY;
    private JSpinner addConSpinnerZ;
    private JSpinner addSpinnerX;
    private JSpinner addSpinnerY;
    private JSpinner addSpinnerZ;
    private JSpinner addSpinnerQ;
    private JSpinner addConSpinnerQ;

    // InputTables.
    private InputTable pt;
    private InputTable containersTable;

    // JCheckBoxes
    private JCheckBox flip;
    private JCheckBox conModel;

    // JTextFields
    private JTextField conNameInput;
    private JTextField nameInput;

    private JComboBox<Integer> constraintLevel;
    private JTextArea insText;
    private JButton jfxBtn;

    private Solution solution;
    private long id = 0;
    private boolean hasModel = false;
    
    /**
     * Constructor.
     */
    public GUI() {
        // init the frames.
        URL url = GUI.class.getResource("/icons/logo.png");
        System.out.println("DEBUG: /icons/logo.png = " + url);

        if (url == null) {
            throw new IllegalStateException("Resource not found: /icons/logo.png. " +
                    "Check that src/main/resources/icons/logo.png exists and is copied to build/resources/main/icons/");
        }
        this.setEditFrame();
        this.setHomeFrame();
    }

    /**
     * run the gui.
     */
    public void run() {
        // Show the JFrame
        this.welcomeFrame.setVisible(true);
        this.editFrame.setVisible(false);
    }


    /**
     * add the needed features to a load JButton.
     * @param load the JButton to which we want to assign the features.
     */
    private void addLoadFeatures(JButton load) {
        load.setText("Load");
        load.setFocusPainted(false);
        load.setIcon(new ImageIcon(getClass().getResource("/icons/load.png")));
        // set onClick action.
        load.addActionListener(e -> {
            // open file explorer.
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
            int option = fileChooser.showDialog(null, "Open");
            if (option == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                //an instance of builder to parse the specified xml file
                DocumentBuilder db = null;
                try {
                    db = dbf.newDocumentBuilder();
                } catch (ParserConfigurationException parserConfigurationException) {
                    parserConfigurationException.printStackTrace();
                }
                Document doc = null;
                try {
                    assert db != null;
                    doc = db.parse(fileToLoad);
                } catch (SAXException | IOException saxException) {
                    saxException.printStackTrace();
                }
                assert doc != null;
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("PACKAGE");

                // convert packages to table.
                for (int itr = 0; itr < nodeList.getLength(); itr++) {
                    Node node = nodeList.item(itr);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        String id = (eElement.getAttribute("id"));
                        double w = Double.parseDouble(eElement.getAttribute("width"));
                        double d = Double.parseDouble(eElement.getAttribute("depth"));
                        double h = Double.parseDouble(eElement.getAttribute("height"));
                        double g = Double.parseDouble(eElement.getAttribute("group"));
                        boolean flip = Boolean.parseBoolean(eElement.getAttribute("canFlip"));
                        String name = eElement.getAttribute("name");
                        PackageTableRow ptr = new PackageTableRow(name, Long.parseLong(id), w, d, h, flip, g);
                        this.pt.add(ptr);
                    }
                }

                NodeList nodeList2 = doc.getElementsByTagName("CONTAINER");

                // convert bins to table
                for (int itr = 0; itr < nodeList2.getLength(); itr++) {
                    Node node = nodeList2.item(itr);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        double w = Double.parseDouble(eElement.getAttribute("width"));
                        double d = Double.parseDouble(eElement.getAttribute("depth"));
                        double h = Double.parseDouble(eElement.getAttribute("height"));
                        double q = Double.parseDouble(eElement.getAttribute("quantity"));
                        boolean model = Boolean.parseBoolean(eElement.getAttribute("canFlip"));
                        String name = eElement.getAttribute("name");
                        this.hasModel = model;
                        ContainerTableRow ctr = new ContainerTableRow(name, w, d, h, q);
                        this.containersTable.add(ctr);
                    }
                }
                this.switchFrame();
            }
        });
    }


    /**
     * set the home screen.
     */
    private void setHomeFrame() {
        // basic features
        this.welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.welcomeFrame.setSize(2 * FRAME_SIZE,FRAME_SIZE);
        this.welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.welcomeFrame.setMinimumSize(new Dimension(2 * FRAME_SIZE, FRAME_SIZE));
        this.welcomeFrame.setIconImage(new ImageIcon(getClass().getResource("/icons/logo.png")).getImage());
        this.welcomeFrame.setLayout(new BorderLayout());
        this.welcomeFrame.setBackground(Color.WHITE);

        // add panels
        JPanel out = new JPanel();
        out.setBackground(Color.WHITE);
        JPanel row1 = new JPanel();
        row1.setBackground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.WHITE);
        out.setLayout(new BoxLayout(out, BoxLayout.Y_AXIS));

        ImageIcon ii = new ImageIcon(getClass().getResource("/icons/logo.png"));
        JLabel iLabel = new JLabel();
        iLabel.setIcon(ii);
        row1.add(iLabel, BorderLayout.CENTER);

        // add make new button.
        JButton make = new JButton("New");
        make.setFocusPainted(false);
        make.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
        make.addActionListener(e -> this.switchFrame());

        // add a load button.
        JButton load = new JButton();
        this.addLoadFeatures(load);

        // add an exit button.
        JButton exit = new JButton("Exit");
        exit.setFocusPainted(false);
        exit.setIcon(new ImageIcon(getClass().getResource("/icons/exit.png")));
        exit.addActionListener(e -> System.exit(1));

        // add to panels.
        row2.add(make);
        row2.add(new Label("\t"));
        row2.add(load);
        row2.add(new Label("\t"));
        row2.add(exit);
        out.add(row1);
        out.add(row2);
        // add to frame.
        this.welcomeFrame.add(out);
    }

    /**
     * set the editing screen.
     */
    private void setEditFrame() {
        // set basic features.
        this.editFrame.setLayout(new BoxLayout(this.editFrame.getContentPane(), BoxLayout.Y_AXIS));
        this.editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.editFrame.setSize(2 * FRAME_SIZE, FRAME_SIZE);
        this.editFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.editFrame.setMinimumSize(new Dimension(2 * FRAME_SIZE, FRAME_SIZE));
        this.editFrame.setIconImage(new ImageIcon(getClass().getResource("/icons/logo.png")).getImage());

        // make visual work area panel.
        JPanel visualWorkArea = new JPanel();
        visualWorkArea.setLayout(new BoxLayout(visualWorkArea, BoxLayout.X_AXIS));

        // make controls panel.
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBackground(Color.white);
        // make rows for the controls panel.
        JPanel row1 = new JPanel();
        row1.setBackground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.WHITE);
        JPanel row3 = new JPanel();
        row3.setBackground(Color.WHITE);

        setContainer(row2);
        setAdd(row3);

        // make buttons.
        JButton load = new JButton();
        this.addLoadFeatures(load);

        JButton reset = new JButton("Reset");
        reset.setIcon(new ImageIcon(getClass().getResource("/icons/reset.png")));
        reset.setFocusPainted(false);

        // add onClick
        reset.addActionListener(e -> {
           this.pt.removeAllRows();
           this.containersTable.removeAllRows();
        });

        // show 3D illustration.
        JButton show3d = new JButton("Show 3D");
        show3d.setFocusPainted(false);
        show3d.setEnabled(false);
        show3d.setIcon(new ImageIcon(getClass().getResource("/icons/3d-model.png")));
        this.jfxBtn = show3d;

        // show instructions.
        JButton instructions = new JButton("Show Packing Instructions");
        instructions.setFocusPainted(false);
        instructions.setEnabled(false);


        this.instructionsFrame.setSize( FRAME_SIZE/2, FRAME_SIZE/2);
        this.insText = new JTextArea();
        JPanel insPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        insPanel.setBackground(Color.WHITE);
        insPanel.add(this.insText);
        JScrollPane insScroll = new JScrollPane(insPanel);
        insScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        insScroll.setPreferredSize(new Dimension(FRAME_SIZE/2, FRAME_SIZE/2));
        this.instructionsFrame.add(insScroll);
        instructions.addActionListener(e -> {
            this.insText.selectAll();
            this.insText.replaceSelection("");
            this.insText.append(this.solution.toString());
            this.instructionsFrame.setVisible(true);

        });

        instructions.setIcon(new ImageIcon(getClass().getResource("/icons/instruction.png")));

        // pack button.
        JButton pack = new JButton("Pack");
        pack.setFocusPainted(false);
        // pack OnClick.
        pack.addActionListener(e -> {
            this.instructionsFrame.setVisible(false);
            int constraint = this.constraintLevel.getSelectedIndex();

            // convert the tables to bins and packages.
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
            // find solution
            if(bins.size() > 0 && packs.size() > 0) {
                PackingProblemSolver3D pm = new PackingProblemSolver3D(new PackingProblemDefinition(bins, packs, constraint));
                this.solution = pm.findBestSolution();
                //System.out.println(this.solution);
                if (this.solution != null) {
                    show3d.setEnabled(true);
                    instructions.setEnabled(true);
                }
            }
        });

        pack.setIcon(new ImageIcon(getClass().getResource("/icons/pack.png")));

        // save button.
        JButton save = new JButton("Save");
        save.setIcon(new ImageIcon(getClass().getResource("/icons/save.png")));
        save.setFocusPainted(false);
        // save action.
        save.addActionListener(e -> {
            // open file explorer.
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
                try {
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    Document doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("DELIVERY");
                    doc.appendChild(rootElement);
                    rootElement.appendChild(this.containersTable.toXml(doc, "CONTAINERS"));
                    rootElement.appendChild(this.pt.toXml(doc, "PACKAGES"));
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(fileToSave.getAbsolutePath() + "-content.xml"));
                    transformer.transform(source, result);
                } catch (ParserConfigurationException | TransformerException pce) {
                    pce.printStackTrace();
                }
            }
        });

        // add to panels.
        row1.add(load);
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

        // create packages table.
        InputTable pt = new InputTable("Packages",
                PackageTableRow.NAMES,
                PackageTableRow.CLASSES);
        this.pt = pt;
        JScrollPane scroller1 = pt.getScroller();
        scroller1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setVisible(true);
        scroller1.setPreferredSize(new Dimension(400,400));

        // make containers (bins) table.
        InputTable containers = new InputTable("Containers",
                ContainerTableRow.NAMES,
                ContainerTableRow.CLASSES);
        this.containersTable = containers;
        JScrollPane scroller2 = containers.getScroller();
        scroller2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setVisible(true);
        scroller2.setPreferredSize(new Dimension(400,400));

        // make a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroller1, scroller2);
        splitPane.setDividerLocation(1600);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0.2);

        visualWorkArea.add(splitPane);

        // add to frame.
        this.editFrame.add(controls);
        this.editFrame.add(visualWorkArea);

    }


    /**
     * set the container editing tools.
     * @param jp JPanel we will add the tool to.
     */
    private void setContainer(JPanel jp) {
        // name field
        JTextField textField = new JTextField(18);
        jp.add(new Label("Name:"));
        jp.add(textField);
        this.conNameInput = textField;

        // set x, y, z and quantity
        SpinnerModel addModel1 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel2 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel3 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel4 = new SpinnerNumberModel(1.0, 1, 10000, 1);

        this.addConSpinnerX = new JSpinner(addModel1);
        this.addConSpinnerY = new JSpinner(addModel2);
        this.addConSpinnerZ = new JSpinner(addModel3);
        this.addConSpinnerQ = new JSpinner(addModel4);
        this.addConSpinnerQ.setEnabled(false);

        // add to JPanel with labels.
        jp.add(new Label("  Width:"));
        jp.add(this.addConSpinnerX);
        jp.add(new Label("  Depth:"));
        jp.add(this.addConSpinnerY);
        jp.add(new Label("  Height:"));
        jp.add(this.addConSpinnerZ);
        jp.add(new Label("  Quantity:"));
        jp.add(this.addConSpinnerQ);

        // container model picker
        jp.add(new Label("  Set As Container Model"));
        JCheckBox jcb1 = new JCheckBox("", true);
        jcb1.addActionListener(e -> this.addConSpinnerQ.setEnabled(!jcb1.isSelected()));
        jcb1.setBackground(Color.WHITE);
        this.conModel = jcb1;
        jp.add(jcb1);

        // add container button.
        JButton addContainer = new JButton("Add Container");
        addContainer.addActionListener(e -> addContainer());
        addContainer.setFocusPainted(false);
        addContainer.setIcon(new ImageIcon(getClass().getResource("/icons/container.png")));
        jp.add(addContainer);

        jp.add(new JLabel(" "));

        // remove containers.
        JButton setContainerModel = new JButton("Remove Container");
        setContainerModel.setIcon(new ImageIcon(getClass().getResource("/icons/rubbish-bin.png")));

        setContainerModel.addActionListener(e -> this.containersTable.remove());
        setContainerModel.setFocusPainted(false);
        jp.add(setContainerModel);
        jp.add(new Label("  "));
    }

    /**
     * add container to the container table.
     */
    private void addContainer() {
        if (this.hasModel) {
            this.containersTable.removeAllRows();
            this.hasModel = false;
        }
        // container name
        String name = this.conNameInput.getText();
        if(name.equals("")) {
            name = "Container";
        }
        // get the w,d,h values.
        String w = this.addConSpinnerX.getValue().toString();
        String d = this.addConSpinnerY.getValue().toString();
        String h = this.addConSpinnerZ.getValue().toString();
        double w1 = Double.parseDouble(w);
        double d1 = Double.parseDouble(d);
        double h1 = Double.parseDouble(h);
        // add to table.
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

    /**
     * set the add package tools.
     * @param jp JPanel we will add the tools to.
     */
    private void setAdd(JPanel jp) {
        // name area
        JTextField textField = new JTextField(18);
        jp.add(new Label("Name:"));
        jp.add(textField);
        this.nameInput = textField;

        // w,d,h and quantity.
        SpinnerModel addModel1 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel2 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel3 = new SpinnerNumberModel(1.0, 1, 10000, 1);
        SpinnerModel addModel4 = new SpinnerNumberModel(1.0, 1, 10000, 1);

        JSpinner as1 = new JSpinner(addModel1);
        JSpinner as2 = new JSpinner(addModel2);
        JSpinner as3 = new JSpinner(addModel3);
        JSpinner as4 = new JSpinner(addModel4);

        // add to th JPanel with labels.
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
        // do not flip box ("this side up").
        jp.add(new Label("  Do Not Flip"));
        JCheckBox jcb1 = new JCheckBox("", false);
        jcb1.setBackground(Color.WHITE);
        this.flip = jcb1;
        jp.add(jcb1);

        // add JButton.
        JButton add = new JButton("Add Package");

        SpinnerModel addModel5 = new SpinnerNumberModel(0, 0, 10000, 1);
        JSpinner groupSpinner = new JSpinner(addModel5);
        jp.add(new Label("  Delivery Group:"));
        jp.add(groupSpinner);
        this.groupSpinner = groupSpinner;
        jp.add(new Label("  "));

        add.addActionListener(e -> addBox());
        add.setFocusPainted(false);
        add.setIcon(new ImageIcon(getClass().getResource("/icons/addpackage.png")));
        jp.add(add);
        jp.add(new Label("  "));

        JButton r = new JButton("Remove Package");
        r.addActionListener(e -> this.pt.remove());
        r.setFocusPainted(false);
        r.setIcon(new ImageIcon(getClass().getResource("/icons/removepackage.png")));

        jp.add(r);
    }

    /**
     * pause the screen
     * @param seconds time to pause in seconds.
     */
    private static void pause(double seconds)
    {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException ignored) {}
    }


    /**
     * switch between home frame to editing frame.
     */
    private void switchFrame() {
        this.editFrame.setVisible(true);
        pause(0.5);
        this.welcomeFrame.setVisible(false);
    }


    /**
     * add a box (package) to the packages table.
     */
    private void addBox() {
        // parse the values.
        double q = Double.parseDouble(this.addSpinnerQ.getValue().toString());
        String name = this.nameInput.getText();
        String x = this.addSpinnerX.getValue().toString();
        String y = this.addSpinnerY.getValue().toString();
        String z = this.addSpinnerZ.getValue().toString();
        boolean f = this.flip.isSelected();
        String g = this.groupSpinner.getValue().toString();

        // add rows to the table.
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

    /**
     * get the show 3D illustration button.
     * @return JButton
     */
    public JButton get3DBtn() {
        return this.jfxBtn;
    }

    /**
     * get the generated solution.
     * @return Solution.
     */
    public Solution getSolution() {
        return this.solution;
    }



}
