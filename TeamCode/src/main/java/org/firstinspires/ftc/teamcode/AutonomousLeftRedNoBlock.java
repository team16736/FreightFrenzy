package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;
import org.firstinspires.ftc.teamcode.actions.HelperActions;

//moves forward to the carousel, spins it, then turns and parks in the storage unit

@Autonomous(name = "No Block Autonomous Red Side Left")
public class AutonomousLeftRedNoBlock extends HelperActions{
    private DriveActions driveActions = null;
    private AttachmentActions attachmentActions = null;
    public void runOpMode() {

        driveActions = new DriveActions(telemetry, hardwareMap);
        attachmentActions = new AttachmentActions(telemetry, hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            drive_ForwardAndStop(driveActions, 0.5, 0.1);
            sleep(100);

            spin_CarouselAndStop(attachmentActions, -0.4, 3);
            sleep(100);

            spin_RightAndStop(driveActions, 0.4, 1.4);
            sleep(100);

            drive_ForwardAndStop(driveActions, 0.5, 1);
            sleep(100);

            strafe_LeftAndStop(driveActions, 0.3, 1.3);
        }
    }
}
