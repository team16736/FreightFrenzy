package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.EncoderActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Wheel Test")
public class WheelTest extends HelperActions {
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    private EncoderActions encoderActions = null;

    public void runOpMode() {
        driveActions = new DriveActions(telemetry, hardwareMap);

        waitForStart();
        if (opModeIsActive()) {
            driveActions.setMotorDirection_Forward();
            driveActions.leftRear.setPower(0.5);
            sleep(5000);
            driveActions.leftRear.setPower(0.0);
            driveActions.rightRear.setPower(0.5);
            sleep(5000);
            driveActions.rightRear.setPower(0.0);
            driveActions.rightFront.setPower(0.5);
            sleep(5000);
            driveActions.rightFront.setPower(0.0);
            driveActions.leftFront.setPower(0.5);
            sleep(5000);
            driveActions.leftFront.setPower(0.0);
        }
    }
}