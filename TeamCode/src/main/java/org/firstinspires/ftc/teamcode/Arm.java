package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Arm {

    private enum ArmLocation {
        Unknown,
        Pickup,     // Ready to pickup minerals
        Front,      // Between pickup and drop
        Drop,       // Ready to drop minerals
        Back,       // Between drop and rest
        Folded      // Arm folded on top of bot
    }

    private enum ArmState {
        Raising,
        Lowering,
        Stopped
    }

    // Arm motor
    private DcMotor armMotor;

    // Limit switches
    private DigitalChannel foldedLimitSwitch;
    private DigitalChannel dropLimitSwitch;
    private DigitalChannel pickupLimitSwitch;

    // Current arm location
    private ArmLocation location = ArmLocation.Unknown;

    // Current arm state
    private ArmState state = ArmState.Stopped;

    public Arm() {
        initialize();
    }

    public void initialize() {
        armMotor = Environment.getHardwareMap().get(DcMotor.class, "motor_arm");
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        foldedLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_arm_rest");
        foldedLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        dropLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_arm_drop");
        dropLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        pickupLimitSwitch = Environment.getHardwareMap().get(DigitalChannel.class, "limit_arm_pickup");
        pickupLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        // Determine starting arm location if possible
        if (foldedLimitSwitch.getState()) {
            location = ArmLocation.Folded;
        }
        else if (dropLimitSwitch.getState()) {
            location = ArmLocation.Drop;
        }
        else if (pickupLimitSwitch.getState()) {
            location = ArmLocation.Pickup;
        }
        else {
            location = ArmLocation.Unknown;
        }

        setState(ArmState.Stopped, 0.0);
    }

    public void raise() {
        double power = 0.5;

        if (foldedLimitSwitch.getState()) {
            location = ArmLocation.Folded;
            power = 0.0;
        }
        else {
            if (state == ArmState.Raising) {
                // If we're already in the process of raising the arm, make sure we stop it when
                // it reaches the drop location
                if ((location == ArmLocation.Unknown || location == ArmLocation.Front) && dropLimitSwitch.getState()) {
                    location = ArmLocation.Drop;
                    power = 0.0;
                }
            }
            else {
                // Transitioning from either stopped or lowering state to raising
                if (location == ArmLocation.Pickup) {
                    location = ArmLocation.Front;
                }
                else if (location == ArmLocation.Front && dropLimitSwitch.getState()) {
                    location = ArmLocation.Drop;
                    power = 0.0;
                }
                else if (location == ArmLocation.Drop) {
                    location = ArmLocation.Back;
                }
            }
        }

        setState(ArmState.Raising, power);
    }

    public void lower() {
        double power = -0.5;

        if (pickupLimitSwitch.getState()) {
            location = ArmLocation.Pickup;
            power = 0.0;
        }
        else {
            if (state == ArmState.Lowering) {
                // If we're already in the process of lowering the arm, make sure we stop it when
                // it reaches the drop location
                if ((location == ArmLocation.Unknown || location == ArmLocation.Back) && dropLimitSwitch.getState()) {
                    location = ArmLocation.Drop;
                    power = 0.0;
                }
            }
            else {
                // Transitioning from either stopped or lowering state to raising
                if (location == ArmLocation.Folded) {
                    location = ArmLocation.Back;
                }
                else if (location == ArmLocation.Back && dropLimitSwitch.getState()) {
                    location = ArmLocation.Drop;
                    power = 0.0;
                }
                else if (location == ArmLocation.Drop) {
                    location = ArmLocation.Front;
                }
            }
        }

        setState(ArmState.Lowering, power);
    }

    // Stops the hook motor
    public void stop() {
        setState(ArmState.Stopped, 0.0);
    }

    private void setState(ArmState newState, double power) {
        armMotor.setPower(power);
        state = newState;
        Environment.getTelemetry().addData("ArmState", "new state: %s, power: 5.2f", state.toString(), armMotor.getPower());
    }
}
