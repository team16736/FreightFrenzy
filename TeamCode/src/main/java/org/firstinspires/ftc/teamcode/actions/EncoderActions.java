package org.firstinspires.ftc.teamcode.actions;
// import lines were omitted. OnBotJava will add them automatically.
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;


final public class EncoderActions{
    DcMotorEx motorFrontL;
    DcMotorEx motorFrontR;
    DcMotorEx motorBackL;
    DcMotorEx motorBackR;
    private static LinearOpMode opModeObj;


    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private ElapsedTime runtime = new ElapsedTime();

    public EncoderActions() {

    }

    public EncoderActions(LinearOpMode opMode, Telemetry opModeTelemetry, HardwareMap opModeHardware) {
        opModeObj = opMode;
        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;
        motorFrontL = hardwareMap.get(DcMotorEx.class, "leftFront");
        motorFrontR = hardwareMap.get(DcMotorEx.class, "rightFront");
        motorBackL = hardwareMap.get(DcMotorEx.class, "leftRear");
        motorBackR = hardwareMap.get(DcMotorEx.class, "rightRear");
    }
    public void encoderDrive(double encoderSpeed, double encoderDistance) {
        resetEncoder();
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 62;
        int totalTicks = (int) (ticksPerInch * encoderDistance);
        motorFrontL.setTargetPosition(totalTicks);
        motorFrontR.setTargetPosition(totalTicks);
        motorBackL.setTargetPosition(totalTicks);
        motorBackR.setTargetPosition(totalTicks);


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        velocity(encoderSpeed, encoderSpeed, encoderSpeed, encoderSpeed);

        // While the Op Mode is running, show the motor's status via telemetry
        isMotorBusy();
    }
    public void encoderDriveSpeedRamp(double encoderSpeed, double encoderDistance, double rampTime) {
        resetEncoder();
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 62;
        int totalTicks = (int) (ticksPerInch * encoderDistance);
        motorFrontL.setTargetPosition(totalTicks);
        motorFrontR.setTargetPosition(totalTicks);
        motorBackL.setTargetPosition(totalTicks);
        motorBackR.setTargetPosition(totalTicks);


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double startTime = runtime.now(TimeUnit.SECONDS);
        double currentTime = startTime;

        // While the Op Mode is running, show the motor's status via telemetry
        while (motorFrontL.isBusy()) {
            if(!(runtime.now(TimeUnit.SECONDS)-startTime<rampTime)){
                currentTime = runtime.now(TimeUnit.SECONDS)-startTime;
            }
            double ramp = (currentTime/rampTime)*encoderSpeed;
            velocity(ramp, ramp, ramp, ramp);
            telemetry.addData("FL is at target", !motorFrontL.isBusy());
            telemetry.addData("FR is at target", !motorFrontR.isBusy());
            telemetry.addData("BL is at target", !motorBackL.isBusy());
            telemetry.addData("BR is at target", !motorBackR.isBusy());
            telemetry.update();
        }
    }
    public void encoderDriveNoTimer(double encoderSpeed, double encoderDistance) {
        resetEncoder();
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 62;
        int totalTicks = (int) (ticksPerInch * encoderDistance);
        motorFrontL.setTargetPosition(totalTicks);
        motorFrontR.setTargetPosition(totalTicks);
        motorBackL.setTargetPosition(totalTicks);
        motorBackR.setTargetPosition(totalTicks);


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        velocity(encoderSpeed, encoderSpeed, encoderSpeed, encoderSpeed);
    }
    public void encoderDriveUntilTape(double encoderSpeed, AttachmentActions attachmentActions){
        resetEncoder();
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 62;
        if(encoderSpeed<0){ticksPerInch = -62;}
        int totalTicks = (int) (ticksPerInch * 90);
        motorFrontL.setTargetPosition(totalTicks);
        motorFrontR.setTargetPosition(totalTicks);
        motorBackL.setTargetPosition(totalTicks);
        motorBackR.setTargetPosition(totalTicks);


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        velocity(encoderSpeed, encoderSpeed, encoderSpeed, encoderSpeed);

        // While the Op Mode is running, show the motor's status via telemetry
        while (motorFrontL.isBusy() && motorFrontR.isBusy() && motorBackL.isBusy() && motorBackR.isBusy() && !attachmentActions.detectBarrier()) {
            telemetry.addData("FL is at target", !motorFrontL.isBusy());
            telemetry.addData("FR is at target", !motorFrontR.isBusy());
            telemetry.addData("BL is at target", !motorBackL.isBusy());
            telemetry.addData("BR is at target", !motorBackR.isBusy());
            telemetry.update();
        }
    }
    public void encoderStrafe(double encoderSpeed,
                              double encoderDistance,
                              boolean encoderMoveLeft) {
        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 64.75;
        int totalTicks = (int) (ticksPerInch * encoderDistance);
        if (encoderMoveLeft){
            motorFrontL.setTargetPosition(-totalTicks);
            motorFrontR.setTargetPosition(totalTicks);
            motorBackL.setTargetPosition(totalTicks);
            motorBackR.setTargetPosition(-totalTicks);
        }else{
            motorFrontL.setTargetPosition(totalTicks);
            motorFrontR.setTargetPosition(-totalTicks);
            motorBackL.setTargetPosition(-totalTicks);
            motorBackR.setTargetPosition(totalTicks);
        }


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        motorFrontL.setVelocity(-encoderSpeed);
        motorFrontR.setVelocity(-encoderSpeed);
        motorBackL.setVelocity(-encoderSpeed);
        motorBackR.setVelocity(-encoderSpeed);

        //motorFrontL.isBusy()hile the Op Mode is running, show the motor's status via telemetry
        while (motorFrontL.isBusy()) {
            telemetry.addData("FL is at target", !motorFrontL.isBusy());
            telemetry.addData("FR is at target", !motorFrontR.isBusy());
            telemetry.addData("BL is at target", !motorBackL.isBusy());
            telemetry.addData("BR is at target", !motorBackR.isBusy());
            telemetry.update();
        }
    }
    public void encoderSpin(double encoderSpeed,
                              double encoderDegrees,
                              boolean encoderSpinLeft) {
        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set the motor's target position to 6.4 rotations
        double ticksPerDegree = 15.5759722;
        int totalTicks = (int) (ticksPerDegree * encoderDegrees);
        if (encoderSpinLeft){
            motorFrontL.setTargetPosition(-totalTicks);
            motorFrontR.setTargetPosition(totalTicks);
            motorBackL.setTargetPosition(-totalTicks);
            motorBackR.setTargetPosition(totalTicks);
        }else{
            motorFrontL.setTargetPosition(totalTicks);
            motorFrontR.setTargetPosition(-totalTicks);
            motorBackL.setTargetPosition(totalTicks);
            motorBackR.setTargetPosition(-totalTicks);
        }


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second
        motorFrontL.setVelocity(-encoderSpeed);
        motorFrontR.setVelocity(-encoderSpeed);
        motorBackL.setVelocity(-encoderSpeed);
        motorBackR.setVelocity(-encoderSpeed);

        //motorFrontL.isBusy()hile the Op Mode is running, show the motor's status via telemetry
        while (motorFrontL.isBusy()) {
            telemetry.addData("FL is at target", !motorFrontL.isBusy());
            telemetry.addData("FR is at target", !motorFrontR.isBusy());
            telemetry.addData("BL is at target", !motorBackL.isBusy());
            telemetry.addData("BR is at target", !motorBackR.isBusy());
            telemetry.update();
        }
    }

//    public void fancySpin(double speed, double distance, boolean moveLeft){
//        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        // Set the motor's target position to 6.4 rotations
//        double ticksPerInch = 64.75;
//        int totalTicks = (int) (ticksPerInch * distance);
//        if (moveLeft){
//            motorFrontL.setTargetPosition(-totalTicks);
//            motorFrontR.setTargetPosition(totalTicks);
//            motorBackL.setTargetPosition(totalTicks);
//            motorBackR.setTargetPosition(-totalTicks);
//        }else{
//            motorFrontL.setTargetPosition(totalTicks); // 2 7/8 rotation
//            motorFrontR.setTargetPosition(-totalTicks / 4);
//            motorBackL.setTargetPosition(-totalTicks);
//            motorBackR.setTargetPosition(totalTicks * 4);
//        }
//
//
//        // Switch to RUN_TO_POSITION mode
//        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        // Start the motor moving by setting the max velocity to 1 revolution per second
//        if (moveLeft) {
//            motorFrontL.setVelocity(-speed / 2);
//            motorFrontR.setVelocity(-speed);
//            motorBackL.setVelocity(-speed * 2);
//            motorBackR.setVelocity(-speed);
//        } else {
//            motorFrontL.setVelocity(-speed);
//            motorFrontR.setVelocity(-speed / 4);
//            motorBackL.setVelocity(-speed);
//            motorBackR.setVelocity(-speed * 4);
//        }
//
//        //motorFrontL.isBusy()hile the Op Mode is running, show the motor's status via telemetry
//        while (motorFrontL.isBusy()) {
//            telemetry.addData("FL is at target", !motorFrontL.isBusy());
//            telemetry.addData("FR is at target", !motorFrontR.isBusy());
//            telemetry.addData("BL is at target", !motorBackL.isBusy());
//            telemetry.addData("BR is at target", !motorBackR.isBusy());
//            telemetry.update();
//        }
//    }

    public void encoderStrafeOld(double speed, double distance, boolean moveLeft) {
        resetEncoder();
        // Set the motor's target position to 6.4 rotations
        double ticksPerInch = 64.75;
        int totalTicks = (int) (ticksPerInch * distance);
        if (moveLeft){
            motorFrontL.setTargetPosition(-totalTicks);
            motorFrontR.setTargetPosition(totalTicks);
            motorBackL.setTargetPosition(totalTicks);
            motorBackR.setTargetPosition(-totalTicks);
            velocity(speed, speed, -speed, -speed);
        }else{
            motorFrontL.setTargetPosition(totalTicks);
            motorFrontR.setTargetPosition(-totalTicks);
            motorBackL.setTargetPosition(-totalTicks);
            motorBackR.setTargetPosition(totalTicks);
            velocity(speed, -speed, speed, -speed);
        }


        // Switch to RUN_TO_POSITION mode
        motorFrontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start the motor moving by setting the max velocity to 1 revolution per second


        isMotorBusy();
        // While the Op Mode is running, show the motor's status via telemetry

    }
    public void isMotorBusy(){
        while (motorFrontL.isBusy() && motorFrontR.isBusy() && motorBackL.isBusy() && motorBackR.isBusy()) {
            telemetry.addData("FL is at target", !motorFrontL.isBusy());
            telemetry.addData("FR is at target", !motorFrontR.isBusy());
            telemetry.addData("BL is at target", !motorBackL.isBusy());
            telemetry.addData("BR is at target", !motorBackR.isBusy());
            telemetry.update();
        }
    }
    public void velocity(double lf, double rf, double lb, double rb){
        motorFrontL.setVelocity(lf);
        motorFrontR.setVelocity(rf);
        motorBackL.setVelocity(lb);
        motorBackR.setVelocity(rb);
    }
    public void resetEncoder(){
        motorFrontL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}

