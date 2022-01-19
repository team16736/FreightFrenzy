
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;
import org.firstinspires.ftc.teamcode.actions.constants.ConfigConstants;

@TeleOp(name = "Experiment Tele Op", group = "Linear Opmode")

public class ExperimentTeleOp extends HelperActions {

    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    DcMotorEx slideExtendMotor;
    boolean memoryBit;
    boolean memBitArmSpin;

    @Override
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        //driveActions.setSpeed(1.0);

        double carouselPower = 0.4;
        int targetArmSpin = 0;
        int speeding = 0;
        double speed = 0.8;
        double speedY; //Create new double for the speed.
        int currentPos; //Create an integer for the current position (IMPORTANT THAT ITS AN INTEGER, WILL NOT WORK OTHERWISE)
        int armUpPosition1 = 0;
        int armUpPosition2 = 0;
        final float[] hsvValues = new float[3];

        slideExtendMotor = hardwareMap.get(DcMotorEx.class, ConfigConstants.SLIDE_EXTEND_MOTOR);
        slideExtendMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveActions.setSpeed(0.8);

        slideExtendMotor.setTargetPosition(-3000);
        slideExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideExtendMotor.setPower(0.5);

        while (opModeIsActive()) {
            //TODO: add functionality for red side carousel

            /** Gamepad 1 **/
            driveActions.drive(
                    gamepad1.left_stick_x,      //joystick controlling strafe
                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                    gamepad1.right_stick_x);    //joystick controlling rotation
            if (gamepad2.x){
                attachmentActions.closeGripper();
            }
            if (gamepad2.y){
                attachmentActions.openGripper();
            }
            if (gamepad2.dpad_right){
                attachmentActions.spinCarousel(-carouselPower);
            } else if (gamepad2.dpad_left){
                attachmentActions.spinCarousel(carouselPower);
            } else{
                attachmentActions.spinCarousel(0.0);
            }

            driveActions.weirdWheelDrive(gamepad1.right_trigger, gamepad1.left_trigger);

            double armSpeed = changeSpeedArm(gamepad2.dpad_up, gamepad2.dpad_down);

            speedY = gamepad2.left_stick_y; //map double speedY to the Y axis of player 1's left joystick.
            currentPos = slideExtendMotor.getCurrentPosition(); //map integer currentPos to the arm's current extended position.
            if(currentPos <= -3450 && speedY < 0) {//limit extending to -3450 encoder ticks, about 1 inch from fully extended. check if you are pressing up to continue extending, if so then set speed to 0.
                speedY = 0;
            }
            if(currentPos >= 0 && speedY > 0) {//limit retracting to 0 ticks, fully closed. check if pressing down on joystick, if so set speed to 0.
                speedY = 0;
                currentPos = 0; //Set the arm to go to the fully closed position. Not needed (dont quote me on this)
            }
            if((speedY == 0) && (!memoryBit)) { //Only runs if speedY is 0 (joystick idle) and the memory bit is false.
                slideExtendMotor.setTargetPosition(currentPos); //Set the arm to hold its position.
                slideExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //Set the arm's run mode so it actually does stuff
                slideExtendMotor.setPower(0.5); //Set the motor to half power.
                memoryBit = true; //Change the memory bit back to true so it only runs once.
            }
            if((speedY != 0)) { //If the joystick IS being pushed, run this code.
                memoryBit = false; //Set memory bit to false so that the previous if statement works.
                slideExtendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODERS); //Change mode to run using encoders.
                slideExtendMotor.setPower(speedY*(armSpeed/.6)); //Set the motor power to the current joystick position.
            }

            telemetry.addData("Gamepad is at", speedY); //testing junk
            telemetry.addData("Arm is extended to", slideExtendMotor.getCurrentPosition()); //testing junk
            if(Math.abs(gamepad2.right_stick_y)>0.1){
                attachmentActions.slideTurnMotor.setPower(gamepad2.right_stick_y * -armSpeed);
                targetArmSpin = attachmentActions.slideTurnMotor.getCurrentPosition();
                armUpPosition1 = targetArmSpin;
                memBitArmSpin = true;
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() < targetArmSpin){
                attachmentActions.slideTurnMotor.setPower((attachmentActions.slideTurnMotor.getCurrentPosition()-targetArmSpin)*-0.01);
                armUpPosition2 = attachmentActions.slideTurnMotor.getCurrentPosition();
                if((armUpPosition2>(armUpPosition1)) && (memBitArmSpin == true)){
                    targetArmSpin = armUpPosition2;
                    memBitArmSpin = false;
                }else{
                    armUpPosition1 = armUpPosition2;
                }
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() > targetArmSpin){
                attachmentActions.slideTurnMotor.setPower((attachmentActions.slideTurnMotor.getCurrentPosition()-targetArmSpin)*-.001);
                armUpPosition2 = attachmentActions.slideTurnMotor.getCurrentPosition();
                if((armUpPosition2<(armUpPosition1)) && (memBitArmSpin == true)){
                    targetArmSpin = armUpPosition2;
                    memBitArmSpin = false;
                }else{
                    armUpPosition1 = armUpPosition2;
                }
            }

            changeSpeed(driveActions, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.a, gamepad1.x, gamepad1.y, gamepad1.b);

            telemetry.addData("Current Position ", attachmentActions.slideTurnMotor.getCurrentPosition());
            telemetry.addData("Target Position", targetArmSpin);
            telemetry.addData("Current Power", attachmentActions.slideTurnMotor.getPower());
            telemetry.addData("Memory Bit", memBitArmSpin);
            telemetry.addData("Position 1", armUpPosition1);
            telemetry.addData("Position 2", armUpPosition2);
            detectColor();
            telemetry.update();
        }
        telemetry.addData("[ROBOTNAME] ", "Going");
        telemetry.update();

        idle();
    }
    private void detectColor(){
        /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
         * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
         * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
         * for an explanation of HSV color. */

        // Update the hsvValues array by passing it to Color.colorToHSV()
        Color.RGBToHSV(attachmentActions.boundaryDetector.red() * 8, attachmentActions.boundaryDetector.green() * 8, attachmentActions.boundaryDetector.blue() * 8, hsvValues);

        telemetry.addLine()
                .addData("Red", attachmentActions.boundaryDetector.red())
                .addData("Green", attachmentActions.boundaryDetector.green())
                .addData("Blue", attachmentActions.boundaryDetector.blue());
        telemetry.addLine()
                .addData("Hue", hsvValues[0])
                .addData("Saturation", hsvValues[1])
                .addData("Value", hsvValues[2]);
//        telemetry.addData("Alpha", "%.3f", colors.alpha);
        if((attachmentActions.boundaryDetector.red()>60) || (attachmentActions.boundaryDetector.green()>60) || (attachmentActions.boundaryDetector.blue()>60)){
            telemetry.addData("Tape"," ");
        }else {
            telemetry.addData("No Tape", " ");
        }
    }
}
