package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;
import org.firstinspires.ftc.teamcode.actions.EncoderActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Autonomous Red Side Right")
public class AutonomousRightRed extends HelperActions{
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    private EncoderActions encoderActions = null;
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        encoderActions = new EncoderActions(this, telemetry, hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            double speed = 762.2;
            encoderActions.encoderDrive(speed, 15);
            encoderActions.encoderStrafe(speed, 2, false);
            placeBlock(encoderActions, attachmentActions, elementDetection(encoderActions, attachmentActions, false));

            weirdWheelsSpeed(driveActions, 1.0);

            encoderActions.encoderDriveUntilTape(3000, attachmentActions);

            encoderActions.resetEncoder();
            weirdWheelsSpeed(driveActions, 0.0);
//            encoderActions.encoderDriveUntilTape(-speed, attachmentActions);
            sleep(100);

            drive_ReverseAndStop(driveActions, 0.5, 0.2);
            attachmentActions.closeGripper();
            sleep(200);
            attachmentActions.openGripper();
            sleep(200);
        }
    }
    private void placeBlock(EncoderActions encoderActions, AttachmentActions attachmentActions, int blockPlace){
        double speed = 762.2;
        if (blockPlace == 1){
            attachmentActions.spinSlide(speed, -48);
            attachmentActions.extendSlide(13);
            encoderActions.encoderSpin(speed, 35, true);
            sleep(1000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 128, false);
            attachmentActions.spinSlide(speed, 48);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed, 6, true);
        } else  if (blockPlace == 2){
            attachmentActions.spinSlide(speed, -33);
            attachmentActions.extendSlide(14);
            encoderActions.encoderStrafe(speed, 8, true);
            encoderActions.encoderSpin(speed, 33, true);
            sleep(1000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 128, false);
            attachmentActions.spinSlide(speed, 35);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed/2, 9, true);
            encoderActions.encoderStrafe(speed, 3, false);
        } else{
            attachmentActions.spinSlide(speed, -15);
            attachmentActions.extendSlide(18);
            encoderActions.encoderStrafe(speed, 8, true);
            encoderActions.encoderSpin(speed, 35, true);
            sleep(1500);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 128, false);
            attachmentActions.spinSlide(speed, 48);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed, 6, true);
        }
    }
}
