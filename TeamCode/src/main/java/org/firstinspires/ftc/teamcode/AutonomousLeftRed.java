package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.EncoderActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Autonomous Red Side Left")
public class AutonomousLeftRed extends HelperActions{
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
            placeBlock(encoderActions, attachmentActions, elementDetection(encoderActions, attachmentActions, true));
        }
    }

    private void placeBlock(EncoderActions encoderActions, AttachmentActions attachmentActions, int blockPlace) {
        double speed = 762.2;
        if (blockPlace == 1) {
            encoderActions.encoderStrafe(speed, 14, true);
            attachmentActions.elbowLevel1();
            sleep(1000);
            encoderActions.encoderDrive(speed, 23);
            encoderActions.encoderSpin(speed, 90, false);
            encoderActions.encoderStrafe(speed, 10, false);
            encoderActions.encoderDrive(speed, 4.5);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(speed, -2.5);
            encoderActions.encoderStrafe(speed, 10, true);
        } else if (blockPlace == 2) {
            encoderActions.encoderStrafe(speed, 14, true);
            attachmentActions.elbowLevel2();
            sleep(1000);
            encoderActions.encoderDrive(speed, 22);
            encoderActions.encoderSpin(speed, 90, false);
            encoderActions.encoderDrive(speed, 15);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(speed, -6);
        } else {
            encoderActions.encoderStrafe(speed, 4, false);
            encoderActions.encoderDrive(speed, 24);
            encoderActions.encoderStrafe(speed, -3, false);
            encoderActions.encoderSpin(speed, 90, false);
            attachmentActions.elbowLevel2();
            sleep(1000);
            encoderActions.encoderDrive(speed, 2);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(speed, -6);
        }
//        attachmentActions.openGripper();
//        sleep(500);
//        encoderActions.encoderDrive(762.2, -6);
        attachmentActions.contractElbow();
//        encoderActions.encoderDrive(762.2, -20);
//        encoderActions.encoderSpin(762.2, 180, true);
//        encoderActions.encoderStrafe(762.2, 33, false);
//        spin_CarouselAndStop(attachmentActions, 0.4, 4);
//        encoderActions.encoderStrafe(762.2,20,true);
        encoderActions.encoderDrive(762.2,-20);
        encoderActions.encoderSpin(762.2,95,false);
        encoderActions.encoderStrafe(762.2, 2, false);
        encoderActions.encoderDrive(762.2,30.5);
        spin_CarouselAndStop(attachmentActions, -0.4,4);
        encoderActions.encoderDrive(762.2,-19);
    }
}
