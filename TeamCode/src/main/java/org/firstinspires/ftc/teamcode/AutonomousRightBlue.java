package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.EncoderActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Autonomous Blue Side Right")
public class AutonomousRightBlue extends HelperActions {
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    private EncoderActions encoderActions = null;

    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        EncoderActions encoderActions = new EncoderActions(this, telemetry, hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            Double speed = 762.2;

            encoderActions.encoderDrive(speed, 15);
            encoderActions.encoderStrafe(speed, 2, false);
            placeBlock(encoderActions, attachmentActions, elementDetection(encoderActions, attachmentActions, false));
            sleep(10000);
        }
    }

    private void placeBlock(EncoderActions encoderActions, AttachmentActions attachmentActions, int blockPlace) {
        if (blockPlace == 1) {
            encoderActions.encoderStrafe(762.2, 11, false);
            attachmentActions.spinSlide(762.2, 30);
            attachmentActions.extendSlide(17);
            encoderActions.encoderDrive(762.2, 23);
            encoderActions.encoderSpin(762.2, 90, true);
            // encoderActions.encoderDrive(762.2, 4);
            sleep(2000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0.0);
            // encoderActions.encoderDrive(762.2, -4);
        } else if (blockPlace == 2) {
            encoderActions.encoderStrafe(762.2, 11, false);
            attachmentActions.spinSlide(762.2, 48);
            attachmentActions.extendSlide(8);
            sleep(1000);
            encoderActions.encoderDrive(762.2, 23);
            encoderActions.encoderSpin(762.2, 90, true);
            encoderActions.encoderDrive(762.2, 18);
            sleep(1000000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0.0);
            encoderActions.encoderDrive(762.2, -1);
        } else {
            encoderActions.encoderStrafe(762.2, 7, true);
            encoderActions.encoderDrive(762.2, 24);
            encoderActions.encoderStrafe(762.2, 3, false);
            encoderActions.encoderSpin(762.2, 90, true);
            attachmentActions.elbowLevel2();
            sleep(1000);
            encoderActions.encoderDrive(762.2, 2);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(762.2, -6);
        }
//        attachmentActions.openGripper();
//        sleep(500);
//        encoderActions.encoderDrive(762.2, -6);
        attachmentActions.contractElbow();
        encoderActions.encoderDrive(762.2, -20);

        encoderActions.encoderSpin(762.2, 180, true);
        encoderActions.encoderStrafe(762.2, 35, false);
        spin_CarouselAndStop(attachmentActions, 0.4, 4);
        encoderActions.encoderStrafe(762.2, 20, true);        //24 inches
    }
}