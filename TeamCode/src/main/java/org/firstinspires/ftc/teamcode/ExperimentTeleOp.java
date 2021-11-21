
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.actions.AttachmentActions;
import org.firstinspires.ftc.teamcode.actions.DriveActions;

@TeleOp(name = "Experiment Tele Op", group = "Linear Opmode")
public class ExperimentTeleOp extends LinearOpMode {
    private DcMotorEx slideMotor;
    private Servo slideServo;

    @Override
    public void runOpMode() {
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slideServo = hardwareMap.get(Servo.class, "slideServo");
        slideServo.setPosition(1.0);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        slideMotor.setDirection(DcMotorEx.Direction.FORWARD);

        while (opModeIsActive()) {
            if (gamepad1.a){
            slideServo.setPosition(.60);
            } else if (gamepad1.b){
                slideServo.setPosition(1.0);
            }
            if(gamepad1.right_bumper) {
                spinSlide(100, -15);
            } else if(gamepad1.left_bumper){
                spinSlide(100, 15);
            } else{
                slideMotor.setPower(gamepad1.left_stick_y/5);
            }

        }
        telemetry.addData("STEPHON ", "Stopping");
        telemetry.update();

        idle();
    }
    private void spinSlide(double speed, double degrees){
        slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        double ticksPerRevolution = 5281.1;
        double ticksPerDegree = (ticksPerRevolution)/360;
        int totalTicks = (int) (ticksPerDegree * degrees);
        slideMotor.setTargetPosition(totalTicks);


        //Switch to RUN_TO_POSITION mode
        slideMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //Start the motor moving by setting the max velocity to 1 revolution per second
        slideMotor.setVelocity(speed);

        //While the Op Mode is running, show the motor's status via telemetry
        while (slideMotor.isBusy()) {
            telemetry.addData("FL is at target", !slideMotor.isBusy());
            telemetry.addData("encoder count", slideMotor.getCurrentPosition());
            telemetry.update();
        }
    }
    public void extendSlide(double distance){
        double maximumDistance = 24; //the distance from the front of the slide fully contracted to the front of the slide fully extended
        double maximumPosition = 0.46; //the servo position when the slide is at maximum
        double minimumPosition = 1.0; //the servo position when the slide is at minimum
        double distanceCorrectorM = (maximumPosition)-(minimumPosition)/(maximumDistance);
        slideServo.setPosition(distanceCorrectorM * distance);
    }
}
