package org.firstinspires.ftc.teamcode.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
public class NewDriveActions {

    public DcMotorEx leftFront;
    public DcMotorEx leftRear;

    public DcMotorEx rightFront;
    public DcMotorEx rightRear;

    public DcMotor weirdWheels;

    //the amount to throttle the power of the motors
    public double THROTTLE = 0.5;

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
    public NewDriveActions(Telemetry opModeTelemetry, HardwareMap opModeHardware ) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        leftFront = hardwareMap.get(DcMotorEx.class, ConfigConstants.FRONT_LEFT);
        leftRear = hardwareMap.get(DcMotorEx.class, ConfigConstants.BACK_LEFT);

        rightFront = hardwareMap.get(DcMotorEx.class, ConfigConstants.FRONT_RIGHT);
        rightRear = hardwareMap.get(DcMotorEx.class, ConfigConstants.BACK_RIGHT);

        weirdWheels = hardwareMap.get(DcMotor.class, ConfigConstants.WEIRD_WHEELS);
        weirdWheels.setDirection(DcMotorSimple.Direction.FORWARD);

        // 2. Set direction
        setMotorDirection_Forward();
    }

    public void setSpeed(double mySpeed){
        THROTTLE = mySpeed;
    }


    /**
     * Drive method to throttle the power
     * @param speedX - the x value of the joystick controlling strafe
     * @param speedY - the y value of the joystick controlling the forward/backward motion
     * @param rotation - the x value of the joystick controlling the rotation
     */
    public void drive(double speedX, double speedY, double rotation){

        double throttledX = speedX * THROTTLE;
        double throttledY = speedY * THROTTLE;
        double throttledRotation = rotation * THROTTLE;

        driveUsingJoyStick(throttledX, throttledY, throttledRotation);
    }

    /**
     * This function makes the mecanum motor drive using the joystick
     * @param speedX - the x value of the joystick controlling strafe
     * @param speedY - the y value of the joystick controlling the forward/backwards motion
     * @param rotation - the x value of the joystick controlling the rotation
     */
    public void driveUsingJoyStick(double speedX, double speedY, double rotation) {

        double frontLeft = speedX + speedY + rotation;
        double frontRight = -speedX + speedY - rotation;

        double backLeft = -speedX + speedY + rotation;
        double backRight = speedX + speedY - rotation;

//        double fl = speedX + speedY + rotation;
//        double fr = -speedX + speedY - rotation;
//        double bl= -speedX + speedY + rotation;
//        double br = speedX + speedY - rotation;

        double max = getMaxPower(frontLeft, frontRight, backLeft, backRight);
        if (max > 1) {
            frontLeft = frontLeft / max;
            frontRight = frontRight / max;
            backLeft = backLeft / max;
            backRight = backRight / max;
        }

        rightFront.setPower(frontRight);
        leftFront.setPower(frontLeft);
        rightRear.setPower(backRight);
        leftRear.setPower(backLeft);
    }

    private double getMaxPower(double frontLeftValue, double frontRightValue, double backLeftValue, double backRightValue) {
        List<Double> valueList = new LinkedList<>();
        valueList.add(frontLeftValue);
        valueList.add(frontRightValue);
        valueList.add(backLeftValue);
        valueList.add(backRightValue);

        return Collections.max(valueList);
    }

    //This methods is meant for AUTONOMOUS
    public void setMotorDirection_Forward() {
        leftFront.setDirection(MotorConstants.REVERSE);
        leftRear.setDirection(MotorConstants.REVERSE);

        rightFront.setDirection(MotorConstants.FORWARD);
        rightRear.setDirection(MotorConstants.FORWARD);
    }

    //This methods is meant for AUTONOMOUS
    public void setMotorDirection_Reverse() {
        leftFront.setDirection(MotorConstants.FORWARD);
        leftRear.setDirection(MotorConstants.FORWARD);

        rightFront.setDirection(MotorConstants.REVERSE);
        rightRear.setDirection(MotorConstants.REVERSE);
    }

    //This methods is meant for AUTONOMOUS
    public void setMotorDirection_StrafeLeft() {
        leftFront.setDirection(MotorConstants.FORWARD);
        leftRear.setDirection(MotorConstants.REVERSE);

        rightFront.setDirection(MotorConstants.FORWARD);
        rightRear.setDirection(MotorConstants.REVERSE);

    }
    //This methods is meant for AUTONOMOUS - Working
    public void setMotorDirection_StrafeRight() {

        leftFront.setDirection(MotorConstants.REVERSE);
        leftRear.setDirection(MotorConstants.FORWARD);

        rightFront.setDirection(MotorConstants.REVERSE);
        rightRear.setDirection(MotorConstants.FORWARD);
    }

    //This methods is meant for AUTONOMOUS
    public void setMotorDirection_SpinLeft() {
        leftFront.setDirection(MotorConstants.FORWARD);
        leftRear.setDirection(MotorConstants.FORWARD);

        rightFront.setDirection(MotorConstants.FORWARD);
        rightRear.setDirection(MotorConstants.FORWARD);
    }

    //This methods is meant for AUTONOMOUS
    public void setMotorDirection_SpinRight() {
        leftRear.setDirection(MotorConstants.REVERSE);
        leftFront.setDirection(MotorConstants.REVERSE);

        rightRear.setDirection(MotorConstants.REVERSE);
        rightFront.setDirection(MotorConstants.REVERSE);
    }

    public void stop() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    public void applyBrake() {
        leftRear.setZeroPowerBehavior(MotorConstants.BRAKE);
        rightRear.setZeroPowerBehavior(MotorConstants.BRAKE);
        leftFront.setZeroPowerBehavior(MotorConstants.BRAKE);
        rightFront.setZeroPowerBehavior(MotorConstants.BRAKE);
    }

    public void driveByTime(LinearOpMode opMode, double speed, double drivingTime) {

        leftRear.setPower(speed);
        rightRear.setPower(speed);
        rightFront.setPower(speed);

        if(applySensorSpeed){

            leftFront.setPower(speed * 1.1); //Speed needed for sensor

        } else {

            leftFront.setPower(speed);  //Speed needed for hooks (this is our normal speed)
        }

        opMode.sleep((long)(1000 * drivingTime)); //Make the opMode wait - while it is driving
    }



    /**
     * Method to drive a specified distance using motor encoder functionality
     *
     * @param inches - The Number Of Inches to Move
     * @param direction - The Direction to Move
     *                  - Valid Directions:
     *                  - MecanumDrivetrain.DIRECTION_FORWARD
     *                  - MecanumDrivetrain.DIRECTION_REVERSE
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_LEFT
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_RIGHT
     * @param power - The desired motor power (most accurate at low powers < 0.25)
     */

    /**
     * Returns true if the robot is moving
     */
    //NOT TESTED
    private void setMotorDirection(int direction){

        if (direction == MotorConstants.DIRECTION_REVERSE){

            setMotorDirection_Reverse();

        } else if (direction == MotorConstants.DIRECTION_STRAFE_LEFT){

            setMotorDirection_StrafeLeft();

        } else if (direction == MotorConstants.DIRECTION_STRAFE_RIGHT){

            setMotorDirection_StrafeRight();

        } else {

            setMotorDirection_Forward();
        }
    }
    public void weirdWheelDrive(double forward, double reverse){
        if (forward > 0.1) {
            weirdWheels.setPower(forward);
        } else if (reverse > 0.1) {
            weirdWheels.setPower(-reverse);
        } else {
            weirdWheels.setPower(0.0);
        }
    }



}