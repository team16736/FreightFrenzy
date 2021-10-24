package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "Autonomous Blue Side Left")
public class AutonomousLeftBlue extends HelperActions{
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            drive_ForwardAndStop(driveActions, 0.5, 0.7);
            sleep(100);

            spin_RightAndStop(driveActions, 0.4, 0.7);
            sleep(100);

            drive_ForwardAndStop(driveActions, 0.5, 0.4);
            sleep(100);

            attachmentActions.elbowLevel2();
            sleep(1000);

            attachmentActions.openGripper();
            sleep(500);

            attachmentActions.contractElbow();
            sleep(500);

            drive_ReverseAndStop(driveActions, 0.5, 0.4);
            sleep(100);

            spin_LeftAndStop(driveActions, 0.4, 2.1);
            sleep(100);

            drive_ForwardAndStop(driveActions, 0.5, 0.3);
            sleep(100);

            weirdWheelsSpeed(driveActions, 1.0);

            drive_ForwardAndStop(driveActions, 0.5, 5.0);

            weirdWheelsSpeed(driveActions, 0.0);
            sleep(100);
        }
    }
}
