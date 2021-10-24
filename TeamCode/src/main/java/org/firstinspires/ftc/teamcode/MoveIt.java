package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="MoveIt", group="Iterative Opmode")
public class MoveIt extends LinearOpMode {
    private DcMotor bL;
    private DcMotor bR;
    private DcMotor fL;
    private DcMotor fR;


    @Override
    public void runOpMode() {

        bL = hardwareMap.get(DcMotor.class, "leftRear");
        bR = hardwareMap.get(DcMotor.class, "rightRear");
        fL = hardwareMap.get(DcMotor.class, "leftFront");
        fR = hardwareMap.get(DcMotor.class, "rightFront");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);

        fR.setDirection(DcMotorSimple.Direction.FORWARD);
        bR.setDirection(DcMotorSimple.Direction.FORWARD);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();

            double power = 0.5;
            fR.setPower(power);
            fL.setPower(power);
            bR.setPower(power);
            bL.setPower(power);
            sleep(1000);
            break;

        }
    }
}
