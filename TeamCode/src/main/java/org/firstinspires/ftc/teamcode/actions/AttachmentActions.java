package org.firstinspires.ftc.teamcode.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.actions.constants.MotorConstants;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
    public Servo elbowServo;
    public Servo gripperServo;
    public DistanceSensor elementDetector;
    public DcMotorEx slideMotor;
    public Servo slideServo;


    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private ElapsedTime runtime = new ElapsedTime();

    public boolean applySensorSpeed = false;

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
        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        gripperServo = hardwareMap.get(Servo.class, ConfigConstants.GRIPPER_SERVO);
        elementDetector = hardwareMap.get(DistanceSensor.class, ConfigConstants.ELEMENT_DETECTOR);
        slideServo = hardwareMap.get(Servo.class, ConfigConstants.SLIDE_SERVO);
        slideMotor = hardwareMap.get(DcMotorEx.class, ConfigConstants.SLIDE_MOTOR);
        elbowServo.setPosition(0.87);
        gripperServo.setPosition(1.0);
        slideServo.setPosition(1.0);
    }

    public void spinCarousel(double speed){
        carouselSpinner.setPower(speed);
    }
    public void stopCarousel(){
        carouselSpinner.setPower(0.0);
    }
    public void expandElbow(){
        elbowServo.setPosition(0.05);
    }
    public void contractElbow(){
        elbowServo.setPosition(0.87);
    }
    public void elbowLevel1(){ elbowServo.setPosition(0.18);}
    public void elbowLevel2(){ elbowServo.setPosition(0.38); }
    public void openGripper(){ gripperServo.setPosition(0.7); }
    public void closeGripper(){ gripperServo.setPosition(1.0); }
    public boolean detectElement(){
        if (elementDetector.getDistance(DistanceUnit.CM)<10){
            return true;
        } else {
            return false;
        }
    }
    private void spinSlide(double speed, double degrees){
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        double ticksPerRevolution = 5281.1;
        double ticksPerDegree = (ticksPerRevolution)/360;
        int totalTicks = (int) (ticksPerDegree * degrees);
        slideMotor.setTargetPosition(totalTicks);


        //Switch to RUN_TO_POSITION mode
        slideMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //Start the motor moving by setting the max velocity to 1 revolution per second
        slideMotor.setVelocity(speed);

        //While the Op Mode is running, show the motor's status via telemetry
        while (slideMotor.isBusy()) {
            telemetry.addData("FL is at target", !slideMotor.isBusy());
            telemetry.addData("encoder count", slideMotor.getCurrentPosition());
            telemetry.update();
        }
    }
    public void extendSlide(double distance){
        double maximumDistance = 24; //the distance from the front of the slide fully contracted to the front of the slide fully extended
        double maximumPosition = 0.46; //the servo position when the slide is at maximum
        double minimumPosition = 1.0; //the servo position when the slide is at minimum
        double distanceCorrectorM = (maximumPosition)-(minimumPosition)/(maximumDistance);
        slideServo.setPosition(distanceCorrectorM * distance);
    }
    public void adjustElbow(double speed){
        elbowServo.setPosition(elbowServo.getPosition()+speed*0.001);
    }
    public void adjustSlide(double speed){
        slideServo.setPosition(slideServo.getPosition()+speed*0.001);
    }
}