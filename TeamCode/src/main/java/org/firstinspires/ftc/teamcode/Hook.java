package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hook {
    // Hook motor
    private DcMotor hookMotor;

    // Limit switches
    private DigitalChannel topLimitSwitch;
    private DigitalChannel bottomLimitSwitch;

    public Hook() {
        hookMotor = Environment.getHardwareMap().get(DcMotor.class, "motor_hook");
        hookMotor.setDirection(DcMotor.Direction.FORWARD);

        topLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_hook_top");
        topLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        bottomLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_hook_bottom");
        bottomLimitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

    // Raises the hook (lowers the bot if attached to lander)
    public void raise() {
        // If we reached the top don't raise hook any further
        if (topLimitSwitch.getState()) {
            stop();
        }
        else {
            hookMotor.setPower(0.5);
            Environment.getTelemetry().addData("Hook", "raise - power: %.2f", hookMotor.getPower());
        }
    }

    // Lowers the hook (raises the bot if attached to lander)
    public void lower() {
        // If we reached the bottom don't lower hook any further
        if (bottomLimitSwitch.getState()) {
            stop();
        }
        else {
            hookMotor.setPower(-1.0);
            Environment.getTelemetry().addData("Hook", "lower - power: %.2f", hookMotor.getPower());
        }
    }

    public void autoRaiseBot(double timeoutSeconds) {
        // To raise the bot we need to lower the hook
        lower();
        ElapsedTime stopwatch = new ElapsedTime();
        while (Environment.getOpMode().opModeIsActive() && !bottomLimitSwitch.getState() && stopwatch.seconds() < timeoutSeconds) {
            Environment.getTelemetry().addData("Hook", "autoRaiseBot: waiting to raise bot");
            Environment.getTelemetry().update();
        }

        Environment.getTelemetry().addData("Hook", "autoRaiseBot: complete");
    }

    public void autoLowerBot(double timeoutSeconds) {
        // To lower the bot we need to raise the hook
        raise();
        ElapsedTime stopwatch = new ElapsedTime();
        while (Environment.getOpMode().opModeIsActive() && !topLimitSwitch.getState() && stopwatch.seconds() < timeoutSeconds) {
            Environment.getTelemetry().addData("Hook", "autoLowerBot: waiting to lower bot %.2f", stopwatch.seconds());
            Environment.getTelemetry().update();
        }
        stop();
        Environment.getTelemetry().addData("Hook", "autoLowerBot: complete");
    }

    // Stops the hook motor
    public void stop() {
        hookMotor.setPower(0);
        Environment.getTelemetry().addData("Hook", "stop - power: %.2f", hookMotor.getPower());
    }
}
