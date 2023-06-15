import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;


import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * JavaFX UI class.
 */
public class JFXUI extends Application {

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private static final int BUFFER = 30;
    private static final int WINDOW_SIZE = 500;
    private GUI gui;

    /**
     * main
     * @param args command line args.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.gui = new GUI();

        // set action listener for the show 3d button in the gui.
        JButton show = this.gui.get3DBtn();
        show.addActionListener(e -> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Stage stage = new Stage();
                SmartGroup group = new SmartGroup();
                Camera camera = new PerspectiveCamera();
                Scene scene = new Scene(group, WINDOW_SIZE, WINDOW_SIZE, true);
                scene.setCamera(camera);
                scene.setFill(Color.SILVER);
                // add packages to the group
                this.addToGroup(group, this.gui.getSolution());
                stage.setScene(scene);
                stage.setTitle("Lambeau - 3D Illustration");

                initMouseControl(group, scene, stage);
                // set rotation keys.
                stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    switch (event.getCode()) {
                        case O -> group.translateZProperty().set(group.getTranslateZ() + 100);
                        case I -> group.translateZProperty().set(group.getTranslateZ() - 100);
                        case S -> group.rotateByX(10);
                        case W -> group.rotateByX(-10);
                        case A -> group.rotateByY(10);
                        case D -> group.rotateByY(-10);
                        case RIGHT -> group.translateXProperty().set(group.getTranslateX() - 10);
                        case LEFT -> group.translateXProperty().set(group.getTranslateX() + 10);
                        case DOWN -> group.translateYProperty().set(group.getTranslateY() - 10);
                        case UP -> group.translateYProperty().set(group.getTranslateY() + 10);
                    }
                });

                // set close action
                stage.setOnCloseRequest(e1 -> {
                    stage.close();
                    stage.setScene(null);
                    show.setEnabled(true);
                });

                stage.show();
                show.setEnabled(false);
            });
        });
        gui.run();
    }

    /**
     * add packages from the solution to the group.
     * @param group the group.
     * @param solution packing solution.
     */
    public void addToGroup(SmartGroup group, Solution solution) {
        // buffer between different bins.
        int gap = BUFFER;
        List<Bin> bins = solution.getBins();
        // add packages from each bin
        for (Bin bin : bins) {
            Map<Location, Package> locMap = bin.getLocations();
            Set<Location> loc = locMap.keySet();
            for (Location l : loc) {
                // set box features.
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(this.genRandomColour());
                material.setDiffuseColor(material.getDiffuseColor().deriveColor(1, 1, 1, 1));
                Orientation o = l.getOrientation();
                Point point = l.getBackBottomLeftPoint();

                Box box = new Box(o.getW(), o.getH(), o.getD());

                // set box location
                box.setTranslateX(point.getX() + (o.getW() >> 1) + gap); // Adjust X translation
                box.setTranslateY((WINDOW_SIZE >> 1) - point.getZ() - (o.getH() >> 1)); // Adjust Y translation
                box.setTranslateZ(- point.getY() - (o.getD() >> 1));
                box.setMaterial(material);
                group.getChildren().add(box);
            }
            // change the gap.
            gap += bin.getWidth() + BUFFER;
        }
    }

    /**
     * get a random colour.
     * @return Colour
     */
    private Color genRandomColour() {
        Random random = new Random();
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        return Color.color(r, g, b);
    }

    /**
     * initialize mouse control - for rotating.
     * @param group group
     * @param scene scene
     * @param stage stage
     */
    private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }
}
