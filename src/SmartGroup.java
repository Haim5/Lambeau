import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * SmartGroup class.
 */
public class SmartGroup extends Group {
    Rotate r;
    Transform t = new Rotate();

    /**
     * rotate the entire group in the X axis.
     * @param ang the angle to rotate to.
     */
    void rotateByX(int ang) {
        r = new Rotate(ang, Rotate.X_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

    /**
     * rotate the entire group in the Y axis.
     * @param ang the angle to rotate to.
     */
    void rotateByY(int ang) {
        r = new Rotate(ang, Rotate.Y_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }
}