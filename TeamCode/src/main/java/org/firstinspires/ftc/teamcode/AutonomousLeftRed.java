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
            attachmentActions.spinSlide(speed, -16);
            attachmentActions.extendSlide(16.5);
            sleep(100);
            encoderActions.encoderDrive(speed, 23);
            sleep(100);
            encoderActions.encoderSpin(speed, 90, false);
            sleep(2000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            attachmentActions.spinSlide(speed, 16);
        } else if (blockPlace == 2) {
            encoderActions.encoderStrafe(speed, 14, true);
            attachmentActions.spinSlide(speed, -33);
            attachmentActions.extendSlide(10);
            encoderActions.encoderDrive(speed, 22);
            encoderActions.encoderSpin(speed, 90, false);
            encoderActions.encoderDrive(speed, 13);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(speed, -4);
            attachmentActions.extendSlide(0);
            attachmentActions.spinSlide(speed, 33);
            encoderActions.encoderSpin(speed, 2, false);
        } else {
            attachmentActions.extendSlide(6.5);
            encoderActions.encoderStrafe(speed, 4, false);
            attachmentActions.spinSlide(speed,-51);
            encoderActions.encoderDrive(speed, 24);
            encoderActions.encoderStrafe(speed, -3, false);
            encoderActions.encoderSpin(speed, 90, false);
            sleep(100);
            attachmentActions.openGripper();
            sleep(500);
            encoderActions.encoderDrive(speed, -6);
            encoderActions.encoderSpin(speed, 5, true);
            attachmentActions.closeGripper();
            attachmentActions.extendSlide(0);
            attachmentActions.spinSlide(speed, 51);
        }
        attachmentActions.extendSlide(0.0);
        encoderActions.encoderDrive(speed,-19);
        encoderActions.encoderSpin(speed,91,false);
        encoderActions.encoderStrafe(speed, 2, false);
        encoderActions.encoderDrive(speed,33);
        spin_CarouselAndStop(attachmentActions, -0.4,4);
        encoderActions.encoderDrive(speed,-20);
    }
}
