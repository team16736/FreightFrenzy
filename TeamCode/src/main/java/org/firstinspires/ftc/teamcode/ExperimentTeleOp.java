
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

@TeleOp(name = "Experiment Tele Op", group = "Linear Opmode")

public class ExperimentTeleOp extends HelperActions {

    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;

    @Override
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        //driveActions.setSpeed(1.0);

        double carouselPower = 0.4;
        int currentTicks = 0;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        attachmentActions.slideTurnMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        double proportionalControl = 0.003;
        double integral = 0;

        driveActions.setSpeed(0.75);

        while (opModeIsActive()) {
            if(gamepad1.a){integral = 0.002;}
            if (gamepad1.b){integral=0.003;}
            if (gamepad1.x){integral=0.0005;}
            if (gamepad1.y){integral=0.001;}
            double armSpeed = changeSpeedArm(gamepad1.dpad_up, gamepad2.dpad_down);
            if(Math.abs(gamepad1.right_stick_y)>0.1){
                attachmentActions.slideTurnMotor.setPower(gamepad1.right_stick_y * -armSpeed);
                currentTicks = attachmentActions.slideTurnMotor.getCurrentPosition();
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() < currentTicks){
                attachmentActions.slideTurnMotor.setPower((attachmentActions.slideTurnMotor.getCurrentPosition()-currentTicks)*-0.003);
            }else if(attachmentActions.slideTurnMotor.getCurrentPosition() > currentTicks){
                attachmentActions.slideTurnMotor.setPower((attachmentActions.slideTurnMotor.getCurrentPosition()-currentTicks)*-integral);
            }// change to proportional control as well may make fewer oscillations

            telemetry.addData("Current Position ", (attachmentActions.slideTurnMotor.getCurrentPosition()/(5281.1/360)));
            telemetry.addData("Target Position", currentTicks);
            telemetry.addData("Current Power", attachmentActions.slideTurnMotor.getPower());
            telemetry.addData("Slide Length", attachmentActions.slideExtendMotor.getCurrentPosition());
            telemetry.update();
        }

        telemetry.addData("[ROBOTNAME] ", "Going");
        telemetry.update();

        idle();
    }
}
