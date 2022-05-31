package body.measure;

/**
 * 計測インタフェース
 * 
 * @author 後藤 聡文
 *
 */
public class Measure {
    public Course course;
    Position position;
    public Touch touch;

    private float brightness;
    private float saturation;
    private float correctSaturation;
    private float correctBrightness;
    private float correctTarget;
    private float leftDistance;
    private float rightDistance;
    private float direction;
    private float motorDirection;
    private boolean isUpped;
    private boolean throwChecker;
    private int leftRotationSpeed;
    private int rightRotationSpeed;

    public Measure(Course course, Position position, Touch touch) {
        this.course = course;
        this.position = position;
        this.touch = touch;
    }

    /// 更新する
    public void update() {
        course.update();
        position.update();
        touch.update();

        leftRotationSpeed = position.getLeftRotationSpeed();
        rightRotationSpeed = position.getRightRotationSpeed();

        setUpped(touch.isUpped());
        setCorrectTarget(course.getCorrectTarget());
        setBrightness(course.getBrightness());
        setSaturation(course.getSaturation());
        setCorrectBrightness(course.getCorrectBrightness());
        setCorrectSaturation(course.getCorrectSaturation());
        setDirection(position.getDirection());
        setLeftDistance(position.getLeftDistance());
        setRightDistance(position.getRightDistance());
        setMotorDirection(position.getMotorDirection());
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getLeftDistance() {
        return leftDistance;
    }

    public void setLeftDistance(float leftDistance) {
        this.leftDistance = leftDistance;
    }

    public float getRightDistance() {
        return rightDistance;
    }

    public void setRightDistance(float rightDistance) {
        this.rightDistance = rightDistance;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getCorrectBrightness() {
        return correctBrightness;
    }

    public void setCorrectBrightness(float correctBrightness) {
        this.correctBrightness = correctBrightness;
    }

    public float getCorrectTarget() {
        return correctTarget;
    }

    public void setCorrectTarget(float correctTarget) {
        this.correctTarget = correctTarget;
    }

    public boolean isUpped() {
        return isUpped;
    }

    public void setUpped(boolean isUpped) {
        this.isUpped = isUpped;
    }

    public float getMotorDirection() {
        return motorDirection;
    }

    public void setMotorDirection(float motorDirection) {
        this.motorDirection = motorDirection;
    }

    public void gyroReset() {
        position.gyroReset();
    }

    public float getCorrectSaturation() {
        return correctSaturation;
    }

    public void setCorrectSaturation(float correctSaturation) {
        this.correctSaturation = correctSaturation;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public boolean isThrowChecker() {
        return throwChecker;
    }

    public void setThrowChecker(boolean throwChecker) {
        this.throwChecker = throwChecker;
    }

    public int getLeftRotationSpeed() {
        return leftRotationSpeed;
    }

    public int getRightRotationSpeed() {
        return rightRotationSpeed;
    }
}
