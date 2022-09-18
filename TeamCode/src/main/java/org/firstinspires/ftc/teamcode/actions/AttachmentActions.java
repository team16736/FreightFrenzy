package org.firstinspires.ftc.teamcode.actions;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.constants.ConfigConstants;

/**
 * Make sure to have the following:
 *
 * 1. Hardware config
 * 2. Setup direction of motors
 * 3. Action method to do something (hookUpDown, drive, etc.,)
 * 4. Helper methods (stop, brake, leftTurn, rightTurn, etc.,)
 *
 * Purpose: Drive the 4 wheels
 */
public class AttachmentActions {

    public DcMotor carouselSpinner;
//    public Servo elbowServo;
    public Servo gripperServo;
    public DistanceSensor elementDetector;
    public ColorSensor boundaryDetector;
    public DcMotorEx slideTurnMotor;
    public DcMotorEx slideExtendMotor;


    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private ElapsedTime runtime = new ElapsedTime();
    final float[] hsvValues = new float[3];

    /**
     * Creates a mecanum motor using the 4 individual motors passed in as the arguments
     * @param opModeTelemetry : Telemetry to send messages to the Driver Control
     * @param opModeHardware : Hardware Mappings
     */
    // Constructor
    public AttachmentActions(Telemetry opModeTelemetry, HardwareMap opModeHardware ) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        carouselSpinner = hardwareMap.get(DcMotor.class, ConfigConstants.CAROUSEL_SPINNER);
//        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        gripperServo = hardwareMap.get(Servo.class, ConfigConstants.GRIPPER_SERVO);
        elementDetector = hardwareMap.get(DistanceSensor.class, ConfigConstants.ELEMENT_DETECTOR);
        boundaryDetector = hardwareMap.get(ColorSensor.class, ConfigConstants.BOUNDARY_DETECTOR);
        slideTurnMotor = hardwareMap.get(DcMotorEx.class, ConfigConstants.SLIDE_TURN_MOTOR);
        slideExtendMotor = hardwareMap.get(DcMotorEx.class, ConfigConstants.SLIDE_EXTEND_MOTOR);
        slideExtendMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        elbowServo.setPosition(0.87);
        gripperServo.setPosition(0.45);
    }

    public void spinCarousel(double speed){
        carouselSpinner.setPower(speed);
    }
    public void stopCarousel(){
        carouselSpinner.setPower(0.0);
    }
    public void contractElbow(){ } //delete 72-74
    public void elbowLevel1(){}
    public void elbowLevel2(){}
    public void openGripper(){ gripperServo.setPosition(0.6); }
    public void closeGripper(){ gripperServo.setPosition(0.45); }
    public boolean detectElement(){
        if (elementDetector.getDistance(DistanceUnit.CM)<8){
            return true;
        } else {
            return false;
        }
    }
    public void spinSlide(double speed, double degrees){
        slideTurnMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        double ticksPerRevolution = 5281.1;
        double ticksPerDegree = (ticksPerRevolution)/360;
        int totalTicks = (int) (ticksPerDegree * degrees);

        slideTurnMotor.setTargetPosition(totalTicks);

        slideTurnMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        slideTurnMotor.setVelocity(speed);
    }

    public void extendSlide(double distance){
        double maxPosition = -3610;
        double minPosition = 0;
        double maxLength = 24;
        double ticksPerInch = (maxPosition-minPosition)/maxLength;
        int totalTicks = (int) (ticksPerInch * distance);

        slideExtendMotor.setTargetPosition(totalTicks);

        slideExtendMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        slideExtendMotor.setPower(0.5);
    }
    public boolean isSlideExtendMotorBusy(){
        return slideExtendMotor.isBusy();
    }
    public boolean isSlideRotateMotorBusy(){
        return  slideTurnMotor.isBusy();
    }
    public void adjustSlide(double speed){}
    public void teleOpSlideRotate(double speed, int distance){
        int currentTicks = slideTurnMotor.getCurrentPosition();
        int totalTicks = (int) currentTicks + distance;
        slideTurnMotor.setTargetPosition(totalTicks);

        //Switch to RUN_TO_POSITION mode
        slideTurnMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //Start the motor moving by setting the max velocity to 1 revolution per second
        slideTurnMotor.setVelocity(speed);
    }
    public boolean detectBarrier(){
        /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
         * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
         * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
         * for an explanation of HSV color. */

        // Update the hsvValues array by passing it to Color.colorToHSV()
        Color.RGBToHSV(boundaryDetector.red() * 8, boundaryDetector.green() * 8, boundaryDetector.blue() * 8, hsvValues);

        telemetry.addLine()
                .addData("Red", boundaryDetector.red())
                .addData("Green", boundaryDetector.green())
                .addData("Blue", boundaryDetector.blue());
        telemetry.addLine()
                .addData("Hue", hsvValues[0])
                .addData("Saturation", hsvValues[1])
                .addData("Value", hsvValues[2]);
//        telemetry.addData("Alpha", "%.3f", colors.alpha);
        if((boundaryDetector.red()>60) || (boundaryDetector.green()>60) || (boundaryDetector.blue()>60)){
            telemetry.addData("Tape"," ");
            return true;
        }else {
            telemetry.addData("No Tape", " ");
            return false;
        }
    }
}