package org.firstinspires.ftc.teamcode.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        elbowServo.setPosition(0.87);
        gripperServo.setPosition(1.0);
    }

    public void spinCarousel(){
        carouselSpinner.setPower(0.6);
    }
    public void stopCarousel(){
        carouselSpinner.setPower(0.0);
    }
    public void expandElbow(){
        elbowServo.setPosition(0.0);
    }
    public void contractElbow(){
        elbowServo.setPosition(0.87);
    }
    public void elbowLevel2(){ elbowServo.setPosition(0.35); }
    public void openGripper(){ gripperServo.setPosition(0.7); }
    public void closeGripper(){ gripperServo.setPosition(1.0); }
}