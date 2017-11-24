package leduyhung.view.myspinner;

/**
 * Created by hungleduy on 11/24/17.
 */

class SpinnerData {

    private String value;
    private float x;
    private float y;

    public SpinnerData() {
    }

    public SpinnerData(String value, float x, float y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}