
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;
import org.firstinspires.ftc.teamcode.actions.NewDriveActions;

@TeleOp(name = "New Chassis TeleOp", group = "Linear Opmode")
public class NewChassisTeleOp extends HelperActions {

    private NewDriveActions newDriveActions = null;
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;

    @Override
    public void runOpMode() {

        newDriveActions = new NewDriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        driveActions = new DriveActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        //newDriveActions.setSpeed(1.0);

        double carouselPower = 0.4;
        int currentTicks = 0;
        int speeding = 0;
        double speed = 0.8;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        newDriveActions.setSpeed(0.8);

        while (opModeIsActive()) {
            //TODO: add functionality for red side carousel

            /** Gamepad 1 **/
            newDriveActions.drive(
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
                attachmentActions.spinCarousel(-0.4);
            } else if (gamepad2.dpad_left){
                attachmentActions.spinCarousel(0.4);
            } else{
                attachmentActions.spinCarousel(0.0);
            }
            attachmentActions.adjustSlide(gamepad2.left_stick_y);
            if(gamepad2.b){attachmentActions.extendSlide(14);}
            newDriveActions.weirdWheelDrive(gamepad2.right_trigger, gamepad2.left_trigger);
            newDriveActions.weirdWheelDrive(gamepad1.right_trigger, gamepad1.left_trigger);

            double armSpeed = changeSpeedArm(gamepad2.dpad_up, gamepad2.dpad_down);
            if(Math.abs(gamepad2.right_stick_y)>0.1){
                attachmentActions.slideTurnMotor.setPower(gamepad2.right_stick_y * -armSpeed);
                currentTicks = attachmentActions.slideTurnMotor.getCurrentPosition();
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() < currentTicks){
                attachmentActions.slideTurnMotor.setPower((attachmentActions.slideTurnMotor.getCurrentPosition()-currentTicks)*-0.003);
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() > currentTicks){
                attachmentActions.slideTurnMotor.setPower(0.0);
            }// change to proportional control as well may make fewer oscillations

            changeSpeed(driveActions, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.a, gamepad1.x, gamepad1.y, gamepad1.b);

            telemetry.addData("Current Position ", attachmentActions.slideTurnMotor.getCurrentPosition());
            telemetry.addData("Target Position", currentTicks);
            telemetry.addData("Current Power", attachmentActions.slideTurnMotor.getPower());
            telemetry.update();
        }

        telemetry.addData("[ROBOTNAME] ", "Going");
        telemetry.update();

        idle();
    }
}
