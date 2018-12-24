package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveBase {

    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;

    public DriveBase()
    {
        motorFrontRight = Environment.getHardwareMap().get(DcMotor.class, "motor_front_right");
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontLeft = Environment.getHardwareMap().get(DcMotor.class, "motor_front_left");
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);

        motorBackRight = Environment.getHardwareMap().get(DcMotor.class, "motor_back_right");
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorBackLeft = Environment.getHardwareMap().get(DcMotor.class, "motor_back_left");
        motorBackLeft.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(double leftPower, double rightPower)
    {
        if (Environment.getBot().getArm().pastLimit()) {
            // Don't drive if arm raised
            return;
        }
        motorBackLeft.setPower(leftPower);
        motorFrontLeft.setPower(leftPower);

        motorBackRight.setPower(rightPower);
        motorFrontRight.setPower(rightPower);

        Environment.getTelemetry().addData("DriveBase", "drive: left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    public void stop() {
        drive(0,0);
    }

    public void drive(double leftPower, double rightPower, double driveTimeInSeconds) {
        drive(leftPower, rightPower);
        ElapsedTime stopwatch = new ElapsedTime();
        while (Environment.getOpMode().opModeIsActive() && stopwatch.seconds() < driveTimeInSeconds) {
            Environment.getTelemetry().addData("DriveBase", "drive: remaining time %.2f", (driveTimeInSeconds - stopwatch.seconds()));
            Environment.getTelemetry().update();
        }

        stop();
        Environment.getTelemetry().addData("DriveBase", "drive: complete");
    }

    public void turnLeft(double power, double turnTimeInSeconds) {
        drive(-power, power, turnTimeInSeconds);
    }

    public void turnRight(double power, double turnTimeInSeconds) {
        drive(power, -power, turnTimeInSeconds);
    }
}
