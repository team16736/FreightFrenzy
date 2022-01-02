package org.firstinspires.ftc.teamcode.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class HelperActions extends LinearOpMode {

    protected ColorSensor right_sensor;
    protected ColorSensor left_sensor;
    protected boolean foundStone = false;
    protected float hsvValues[] = {0F,0F,0F};

    public final double SPEED = 0.5;

    public static int LEFT = 1;
    public static int RIGHT = 2;
    public static int FORWARDS = 3;
    public static int BACKWARDS = 4;

    private int speeding = 0;
    private double speed = 0.8;
    private int speedingArm = 0;
    private double speedArm = 0.6;

    public void drive_ReverseAndStop(DriveActions driveActions, double speed, double drivingTime) {

        driveActions.setMotorDirection_Reverse();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();
    }

    public void drive_ForwardAndStop(DriveActions driveActions, double speed, double drivingTime) {
        driveActions.setMotorDirection_Forward();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();
    }

    public void strafe_RightAndStop(DriveActions driveActions, double speed, double drivingTime) {

        driveActions.setMotorDirection_StrafeRight();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();
    }

    public void strafe_LeftAndStop(DriveActions driveActions, double speed, double drivingTime) {

        driveActions.setMotorDirection_StrafeLeft();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();

        telemetry.addData("strafe_LeftAndStop: ", "-->strafe_LeftAndStop");
        telemetry.update();

    }
    public void spin_LeftAndStop(DriveActions driveActions, double speed, double drivingTime) {
        driveActions.setMotorDirection_SpinLeft();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();
    }

    public void spin_RightAndStop(DriveActions driveActions, double speed, double drivingTime) {
        driveActions.setMotorDirection_SpinRight();
        driveActions.driveByTime(this, speed, drivingTime);
        driveActions.stop();
    }

    public void spin_CarouselAndStop(AttachmentActions attachmentActions, double speed, double time){
        attachmentActions.spinCarousel(speed);
        sleep((long)(time*1000));
        attachmentActions.stopCarousel();
    }
    public void setWheelSpeed(DriveActions driveActions, double speed){
        driveActions.leftRear.setPower(speed);
        driveActions.rightRear.setPower(speed);
        driveActions.rightFront.setPower(speed);
        driveActions.leftFront.setPower(speed);
    }
    public void setWheelSpeedLinear(DriveActions driveActions, double speed, double direction){
        if (direction == LEFT){
            driveActions.setMotorDirection_StrafeLeft();
        } else if (direction == RIGHT){
            driveActions.setMotorDirection_StrafeRight();
        } else if (direction == FORWARDS){
            driveActions.setMotorDirection_Forward();
        } else if (direction == BACKWARDS){
            driveActions.setMotorDirection_Reverse();
        }
        setWheelSpeed(driveActions, speed);
    }
    public void setWheelSpeedSpin(DriveActions driveActions, double speed){
        if (speed == LEFT){
            driveActions.setMotorDirection_SpinLeft();
        } else if (speed == RIGHT){
            driveActions.setMotorDirection_SpinRight();
        }
        setWheelSpeed(driveActions, speed);
    }
    public void weirdWheelsSpeed(DriveActions driveActions, double speed){
        driveActions.weirdWheels.setPower(speed);
    }
    public boolean strafeAndDetect(EncoderActions encoderActions, AttachmentActions attachmentActions, double speed, double distance, boolean strafeLeft){
        encoderActions.motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderActions.motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderActions.motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderActions.motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 64.75;
        int totalTicks = (int) (ticksPerInch * distance);
        if (strafeLeft){
            encoderActions.motorFrontL.setTargetPosition(-totalTicks);
            encoderActions.motorFrontR.setTargetPosition(totalTicks);
            encoderActions.motorBackL.setTargetPosition(totalTicks);
            encoderActions.motorBackR.setTargetPosition(-totalTicks);
        }else{
            encoderActions.motorFrontL.setTargetPosition(totalTicks);
            encoderActions.motorFrontR.setTargetPosition(-totalTicks);
            encoderActions.motorBackL.setTargetPosition(-totalTicks);
            encoderActions.motorBackR.setTargetPosition(totalTicks);
        }


        // Switch to RUN_TO_POSITION mode
        encoderActions.motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        encoderActions.motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        encoderActions.motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        encoderActions.motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        encoderActions.motorFrontL.setVelocity(-speed);
        encoderActions.motorFrontR.setVelocity(-speed);
        encoderActions.motorBackL.setVelocity(-speed);
        encoderActions.motorBackR.setVelocity(-speed);

        //motorFrontL.isBusy()hile the Op Mode is running, show the motor's status via telemetry
        while (encoderActions.motorFrontL.isBusy()) {
            telemetry.addData("FL is at target", !encoderActions.motorFrontL.isBusy());
            telemetry.addData("FR is at target", !encoderActions.motorFrontR.isBusy());
            telemetry.addData("BL is at target", !encoderActions.motorBackL.isBusy());
            telemetry.addData("BR is at target", !encoderActions.motorBackR.isBusy());
            telemetry.update();
            if (attachmentActions.detectElement()){
                if (attachmentActions.detectElement()){
                    return true;
                }
            }
        }
        return false;
    }
//    public void fancySpinRight(EncoderActions encoderActions, double speed, double distance){
//        encoderActions.fancySpin(speed, distance, false);
//    }
//    public void fancySpinLeft(EncoderActions encoderActions, double speed, double distance){
//        encoderActions.fancySpin(speed, distance, true);
//    }
    public int elementDetection(EncoderActions encoderActions, AttachmentActions attachmentActions, boolean strafeLeft) {
        if (attachmentActions.detectElement()) {
            return 1;
        } else if (strafeAndDetect(encoderActions, attachmentActions, 762.2, 11, strafeLeft)) {
            return 2;
        } else {
            return 3;
        }
    }
    public void changeSpeed(DriveActions driveActions, boolean upOne1, boolean downOne1, boolean upTwo, boolean upOne2, boolean downTwo, boolean downOne2){
        if(upOne1){
            speeding++;
            if(speeding == 1){
                speed = speed + 0.1;
            }
        }
        if(downOne1){
            speeding++;
            if(speeding == 1){
                speed = speed - 0.1;
            }
        }
        if(upTwo){
            speeding++;
            if(speeding == 1){
                speed = speed + 0.2;
            }
        }
        if(upOne2){
            speeding++;
            if(speeding == 1){
                speed = speed + 0.1;
            }
        }
        if(downTwo){
            speeding++;
            if(speeding == 1){
                speed = speed - 0.2;
            }
        }
        if(downOne2){
            speeding++;
            if(speeding == 1){
                speed = speed - 0.1;
            }
        }
        if(!upOne1 && !downOne1 && !upTwo && !upOne2 && !downTwo && !downOne2){
            speeding = 0;
        }
        if (speed < 0){
            speed = 0;
        }
        if (speed > 1.0){
            speed = 1.0;
        }
        driveActions.setSpeed(speed);
        telemetry.addData("speed: ", speed);
    }
    public double changeSpeedArm(boolean up, boolean down) {
        if (up) {
            speedingArm++;
            if (speedingArm == 1) {
                speedArm = speedArm + 0.1;
            }
        }
        if (down) {
            speedingArm++;
            if (speedingArm == 1) {
                speedArm = speedArm - 0.1;
            }
        }
        if (!up && !down) {
            speedingArm = 0;
        }
        if (speedArm < 0) {
            speedArm = 0;
        }
        if (speedArm > 1.0) {
            speedArm = 1.0;
        }
        return speedArm;
    }
}