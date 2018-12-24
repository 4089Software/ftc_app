package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Arm {

    private static final double MIN_POWER = 0.3;
    private static final double MAX_POWER = 0.7;
    private static final double INTERPOLATION_TIME = 1.0;
    private static final double ARM_LIMIT = 2800;

    private enum ArmState {
        Raising,
        Lowering,
        Stopped
    }

    // Arm motor
    private DcMotor armMotor;

    // Limit switches
    private DigitalChannel pickupLimitSwitch;

    // Current arm state
    private ArmState state = ArmState.Stopped;

    private ElapsedTime stateTime = new ElapsedTime();

    // Target power
    private double targetPower = 0.0;
    private double currentPower = 0.0;


    public Arm() {
        initialize();
    }

    public void initialize() {
        armMotor = Environment.getHardwareMap().get(DcMotor.class, "motor_arm");
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pickupLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_arm_pickup");
        pickupLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        setState(ArmState.Stopped);
    }

    public void reset() {
        if (!pickupLimitSwitch.getState()) {
            armMotor.setPower(-0.8);

            ElapsedTime stopwatch = new ElapsedTime();
            while (Environment.getOpMode().opModeIsActive() && !pickupLimitSwitch.getState() && stopwatch.seconds() < 5) {
                Environment.getTelemetry().addData("Arm", "reset: waiting to lower arm");
                Environment.getTelemetry().update();
            }
        }

        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Environment.getTelemetry().addData("Arm", "reset: complete");
        Environment.getTelemetry().update();
    }

    public void raise() {
        setState(ArmState.Raising);
    }

    public void lower() {
        if (pickupLimitSwitch.getState()) {
            setState(ArmState.Stopped);
        }
        else {
            setState(ArmState.Lowering);
        }
    }

    public boolean pastLimit() {
        return armMotor.getCurrentPosition() > ARM_LIMIT;
    }

    public void stop() {
        setState(ArmState.Stopped);
    }

    private void setState(ArmState newState) {
        double direction = newState == ArmState.Lowering ? -1.0 : 1.0;

        if (state != newState) {
            stateTime.reset();
            state = newState;
            armMotor.setPower(0.0); // If there's a change in state, stop motor first
        }

        if (state != ArmState.Stopped) {
            // Motor should take INTERPOLATION_TIME seconds to reach MAX_POWER
            double elapsedTime = Math.min(stateTime.seconds(), INTERPOLATION_TIME);
            double percentOfTarget = elapsedTime/INTERPOLATION_TIME;
            targetPower = direction * MAX_POWER;
            currentPower = direction * (MIN_POWER + ((MAX_POWER - MIN_POWER) * percentOfTarget));
        }
        else {
            targetPower = 0.0;
            currentPower = 0.0; // don't slow down to a stop, stop immediately
        }

        if (pickupLimitSwitch.getState()) {
            // if we hit the pickup switch, zero out the arm's encoder position
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        armMotor.setPower(currentPower);

        Environment.getTelemetry().addData("Arm", "state: %s (%5.2f), power: %5.2f -> %5.2f (%5.2f), position: %d, limit: %b",
                state.name(),
                stateTime.seconds(),
                currentPower,
                targetPower,
                armMotor.getPower(),
                armMotor.getCurrentPosition(),
                pickupLimitSwitch.getState());
    }
}
