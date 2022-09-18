package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
            attachmentActions.slideTurnMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            encoderActions.encoderDrive(speed, 15);
            encoderActions.encoderStrafe(speed, 2, false);
            placeBlock(encoderActions, attachmentActions, elementDetection(encoderActions, attachmentActions, false));
            sleep(10000);
        }
    }

    private void placeBlock(EncoderActions encoderActions, AttachmentActions attachmentActions, int blockPlace) {
        Double speed = 762.2;
        sleep(100);
        if (blockPlace == 1) {
            encoderActions.encoderStrafe(speed, 11, false);
            attachmentActions.spinSlide(speed, -46);
            attachmentActions.extendSlide(11);
            encoderActions.encoderDrive(speed, 23);
            encoderActions.encoderSpin(speed, 90, true);
            sleep(200);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            sleep(200);
            attachmentActions.spinSlide(speed, 46);
        } else if (blockPlace == 2) {
            encoderActions.encoderStrafe(speed, 11, false);
            sleep(100);
            attachmentActions.spinSlide(speed, -33);
            attachmentActions.extendSlide(10);
            sleep(1000);
            encoderActions.encoderDrive(speed, 23);
            encoderActions.encoderSpin(speed, 90, true);
            encoderActions.encoderDrive(speed, 12);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            sleep(200);
            attachmentActions.spinSlide(speed, 33);
            encoderActions.encoderDrive(speed, -5);
        } else {
            encoderActions.encoderStrafe(speed, 7, true);
            attachmentActions.spinSlide(speed, -13);
            attachmentActions.extendSlide(12);
            sleep(200);
            encoderActions.encoderDrive(speed, 24);
            sleep(200);
            encoderActions.encoderStrafe(speed, 3, false);
            sleep(200);
            encoderActions.encoderSpin(speed, 90, true);
            sleep(200);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            attachmentActions.spinSlide(speed, 13);
            encoderActions.encoderDrive(speed, -6);
        }
//        attachmentActions.openGripper();
//        sleep(500);
//        encoderActions.encoderDrive(speed, -6);
        encoderActions.encoderDrive(speed, -17.5);

        encoderActions.encoderSpin(speed, 180, true);
        encoderActions.encoderStrafe(speed, 35, false);
        spin_CarouselAndStop(attachmentActions, 0.4, 4);
        encoderActions.encoderStrafe(speed, 20, true);
    }
}