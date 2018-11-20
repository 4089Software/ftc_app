package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

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
        motorBackLeft.setPower(leftPower);
        motorFrontLeft.setPower(leftPower);

        motorBackRight.setPower(rightPower);
        motorFrontRight.setPower(rightPower);

        Environment.getTelemetry().addData("DriveBase", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }
}
