package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

import java.util.List;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "No Block Autonomous Blue Side Right")
public class AutonomousRightBlueNoBlock extends HelperActions{
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("reached", 0);
            telemetry.update();
            drive_ForwardAndStop(driveActions, 0.5, 0.1);
            sleep(100);
            telemetry.addData("reached", 1);
            telemetry.update();

            spin_CarouselAndStop(attachmentActions, 0.4, 3);
            sleep(100);
            telemetry.addData("reached", 2);
            telemetry.update();

            spin_LeftAndStop(driveActions, 0.4, 1.4);
            sleep(100);

            drive_ForwardAndStop(driveActions, 0.5, 1);
            sleep(100);

            strafe_RightAndStop(driveActions, 0.3, 1.3);
        }
    }
}
