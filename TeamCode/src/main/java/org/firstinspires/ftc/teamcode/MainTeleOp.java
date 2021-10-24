
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

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveActions.setSpeed(0.75);

        while (opModeIsActive()) {

            /** Gamepad 1 **/
            driveActions.drive(
                    gamepad1.left_stick_x,      //joystick controlling strafe
                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                    gamepad1.right_stick_x);    //joystick controlling rotation

            if (gamepad1.a){
                attachmentActions.spinCarousel();
            } else{
                attachmentActions.stopCarousel();
            }
            if (gamepad1.b){
                attachmentActions.expandElbow();
            }
            if (gamepad1.a){
                attachmentActions.contractElbow();
            }
            if (gamepad1.x){
                attachmentActions.closeGripper();
            }
            if (gamepad1.y){
                attachmentActions.openGripper();
            }
            if (gamepad2.a){
                attachmentActions.spinCarousel();
            } else{
                attachmentActions.stopCarousel();
            }
            if (gamepad2.b){
                attachmentActions.expandElbow();
            }
            if (gamepad2.a){
                attachmentActions.contractElbow();
            }
            if (gamepad2.x){
                attachmentActions.closeGripper();
            }
            if (gamepad2.y){
                attachmentActions.openGripper();
            }
            driveActions.weirdWheelDrive(gamepad2.right_trigger, gamepad2.left_trigger);
            driveActions.weirdWheelDrive(gamepad1.right_trigger, gamepad1.left_trigger);
            telemetry.addData("position: ", attachmentActions.elbowServo.getPosition());

            //todo - This method simple prints true/false.
            //sensorControlActions.isLimitSwitchPressed();

            //todo - This method moves the linear slide up a little bit
//            armActions                                                                                                                                                                                                                                                                                                                                                                                                                .isLimitSwitchPressed();

            telemetry.update();
        }

        telemetry.addData("STEPHON ", "Stopping");
        telemetry.update();

        idle();
    }
}
