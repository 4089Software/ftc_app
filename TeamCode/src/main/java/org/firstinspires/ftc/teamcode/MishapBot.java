package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class MishapBot {

    private HardwareMap hardwareMap;

    private DcMotor motorBackLeft = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorFrontLeft = null;
    private DcMotor motorFrontRight = null;

    private DcMotor motorHook = null;

    private DigitalChannel limitHookTop = null;
    private DigitalChannel limitHookBottom = null;

    private DigitalChannel limitArmRest = null;
    private DigitalChannel limitArmPickup = null;
    private DigitalChannel limitArmDrop = null;

    public MishapBot(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);

        motorBackRight = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorBackLeft = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackLeft.setDirection(DcMotor.Direction.FORWARD);

        motorHook = hardwareMap.get(DcMotor.class, "motor_hook");
        motorHook.setDirection(DcMotor.Direction.FORWARD);

        limitHookTop = hardwareMap.get(DigitalChannel.class, "limit_hook_top");
        limitHookTop.setMode(DigitalChannel.Mode.INPUT);

        limitHookBottom = hardwareMap.get(DigitalChannel.class, "limit_hook_bottom");
        limitHookBottom.setMode(DigitalChannel.Mode.INPUT);

        //limitArmRest = hardwareMap.get(DigitalChannel.class, "limit_arm_rest");
        //limitArmRest.setMode(DigitalChannel.Mode.INPUT);

        //limitArmPickup = hardwareMap.get(DigitalChannel.class, "limit_arm_pickup");
        //limitArmPickup.setMode(DigitalChannel.Mode.INPUT);

        //limitArmDrop = hardwareMap.get(DigitalChannel.class, "limit_arm_drop");
        //limitArmDrop.setMode(DigitalChannel.Mode.INPUT);
    }

    public void drive(double leftPower, double rightPower)
    {
        motorBackLeft.setPower(leftPower);
        motorFrontLeft.setPower(leftPower);

        motorBackRight.setPower(rightPower);
        motorFrontRight.setPower(rightPower);
    }

    // Lowers the bot
    public void raiseHook(double power)
    {
        // Power needs to be positive
        if (power < 0) return;

        // If we reached the top don't raise hook any further
        if (limitHookTop.getState() == true) return;

        motorHook.setPower(power);
    }

    // Raises the bot
    public void lowerHook(double power)
    {
        // Power needs to be negative
        if (power > 0) return;

        // If we reached the bottom don't lower hook any further
        if (limitHookBottom.getState() == true) return;

        motorHook.setPower(power);
    }

    public void stopHook()
    {
        motorHook.setPower(0);
    }
}
