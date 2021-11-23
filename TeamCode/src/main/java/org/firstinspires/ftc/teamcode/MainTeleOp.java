
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;

@TeleOp(name = "Main Tele Op", group = "Linear Opmode")
public class MainTeleOp extends LinearOpMode {

    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;

    @Override
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        //driveActions.setSpeed(1.0);

        double carouselPower = 0.4;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveActions.setSpeed(0.75);

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
                attachmentActions.spinCarousel(-0.4);
            } else if (gamepad2.dpad_left){
                attachmentActions.spinCarousel(0.4);
            } else{
                attachmentActions.spinCarousel(0.0);
            }
            if (gamepad1.x){
                driveActions.setSpeed(1.0);
            } else if (gamepad1.a){
                driveActions.setSpeed(0.75);
            }
            attachmentActions.adjustSlide(gamepad2.left_stick_y);
            driveActions.weirdWheelDrive(gamepad2.right_trigger, gamepad2.left_trigger);
            driveActions.weirdWheelDrive(gamepad1.right_trigger, gamepad1.left_trigger);
            attachmentActions.slideMotor.setPower(gamepad2.right_stick_y * 0.3);

            telemetry.update();
        }

        telemetry.addData("STEPHON ", "Stopping");
        telemetry.update();

        idle();
    }
}
