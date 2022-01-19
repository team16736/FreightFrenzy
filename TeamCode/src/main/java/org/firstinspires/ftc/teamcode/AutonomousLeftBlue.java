package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.EncoderActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Autonomous Blue Side Left")
public class AutonomousLeftBlue extends HelperActions{
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
            placeBlock(encoderActions, attachmentActions, elementDetection(encoderActions, attachmentActions, true));

            weirdWheelsSpeed(driveActions, 1.0);

            encoderActions.encoderDriveUntilTape(3000, attachmentActions);

            weirdWheelsSpeed(driveActions, 0.0);
            encoderActions.encoderDriveUntilTape(-speed, attachmentActions);
            sleep(100);

            sleep(100);
        }
    }
    private void placeBlock(EncoderActions encoderActions, AttachmentActions attachmentActions, int blockPlace){
        double speed = 762.2;
        if(blockPlace == 1){
            attachmentActions.spinSlide(speed, -15);
            attachmentActions.extendSlide(17);
            encoderActions.encoderSpin(speed, 32, false);
            sleep(1500);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 125, true);
            attachmentActions.spinSlide(speed, 15);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed, 6, false);
        } else if(blockPlace == 2){
            attachmentActions.spinSlide(speed, -33);
            attachmentActions.extendSlide(15);
            encoderActions.encoderStrafe(speed, 8, false);
            encoderActions.encoderSpin(speed, 33, false);
            sleep(2000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 128, true);
            attachmentActions.spinSlide(speed, 33);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed/2, 9, false);
            encoderActions.encoderStrafe(speed, 3, true);
        } else{
            attachmentActions.spinSlide(speed, -46);
            attachmentActions.extendSlide(12);
            encoderActions.encoderStrafe(speed, 8, false);
            encoderActions.encoderSpin(speed, 35, false);
            sleep(1000);
            attachmentActions.openGripper();
            sleep(500);
            attachmentActions.extendSlide(0);
            encoderActions.encoderSpin(speed, 128, true);
            attachmentActions.spinSlide(speed, 48);
            encoderActions.encoderDrive(speed, 8.5);
            encoderActions.encoderStrafe(speed, 6, false);
        }
    }
}
