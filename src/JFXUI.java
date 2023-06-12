import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
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
    private GUI gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.gui = new GUI();


        JButton show = this.gui.get3DBtn();
        show.addActionListener(e -> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Stage stage = new Stage();
                SmartGroup group = new SmartGroup();
                Camera camera = new PerspectiveCamera();
                Scene scene = new Scene(group, 500, 500);
                scene.setCamera(camera);
                scene.setFill(Color.SILVER);
                this.addToGroup(group, this.gui.getSolution());
                stage.setScene(scene);
                stage.setTitle("Lambeau - 3D Illustration");

                initMouseControl(group, scene, stage);
                stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    switch (event.getCode()) {
                        case O:
                            group.translateZProperty().set(group.getTranslateZ() + 100);
                            break;
                        case I:
                            group.translateZProperty().set(group.getTranslateZ() - 100);
                            break;
                        case S:
                            group.rotateByX(10);
                            break;
                        case W:
                            group.rotateByX(-10);
                            break;
                        case A:
                            group.rotateByY(10);
                            break;
                        case D:
                            group.rotateByY(-10);
                            break;
                        case RIGHT:
                            group.translateXProperty().set(group.getTranslateX() - 10);
                            break;
                        case LEFT:
                            group.translateXProperty().set(group.getTranslateX() + 10);
                            break;
                        case DOWN:
                            group.translateYProperty().set(group.getTranslateY() - 10);
                            break;
                        case UP:
                            group.translateYProperty().set(group.getTranslateY() + 10);
                            break;
                    }
                });


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

    public void addToGroup(Group group, Solution solution) {
        int gap = BUFFER;
        List<Bin> bins = solution.getBins();
        for (Bin bin : bins) {
            Map<Location, Package> locMap = bin.getLocations();
            Set<Location> loc = locMap.keySet();

            for (Location l : loc) {
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(this.genRandomColour());
                material.setDiffuseColor(material.getDiffuseColor().deriveColor(1, 1, 1, 1));
                Orientation o = l.getOrientation();
                Point point = l.getBackBottomLeftPoint();

                Box box = new Box(o.getW(), o.getH(), o.getD());
                box.translateXProperty().set(point.getX() + gap);
                box.translateYProperty().set(point.getY() + BUFFER);
                box.translateZProperty().set(point.getZ());
                box.setMaterial(material);
                group.getChildren().add(box);
            }
            gap += bin.getWidth() + BUFFER;
        }
    }

    private Color genRandomColour() {
        Random random = new Random();
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        return Color.color(r, g, b);
    }

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
