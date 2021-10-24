package org.firstinspires.ftc.teamcode.actions;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.AttachmentActions;

public abstract class HelperActions extends LinearOpMode {

    protected ColorSensor right_sensor;
    protected ColorSensor left_sensor;
    protected boolean foundStone = false;
    protected float hsvValues[] = {0F,0F,0F};

    public final double SPEED = 0.5;

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
        attachmentActions.spinCarousel();
        sleep((long)(time*1000));
        attachmentActions.stopCarousel();
    }
    public void setWheelSpeed(DriveActions driveActions, double speed){
        driveActions.leftRear.setPower(speed);
        driveActions.rightRear.setPower(speed);
        driveActions.rightFront.setPower(speed);
        driveActions.leftFront.setPower(speed);
    }
    public void weirdWheelsSpeed(DriveActions driveActions, double speed){
        driveActions.weirdWheels.setPower(speed);
    }
}