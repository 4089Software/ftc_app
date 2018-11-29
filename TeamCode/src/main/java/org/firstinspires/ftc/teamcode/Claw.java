package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    public enum ClawDirection {
        Left,
        Right
    }

    private Servo clawServo;
    private String servoName;
    private ClawDirection clawDirection;

    private static final double LEFT_OPEN_POSITION = 0.05;
    private static final double LEFT_CLOSED_POSITION = 0.35;
    
    private static final double RIGHT_OPEN_POSITION = 0.75;
    private static final double RIGHT_CLOSED_POSITION = 0.45;

    private double targetPosition = 0.0;
    private boolean stopped = true;

    public Claw(String name, ClawDirection direction) {
        servoName = name;
        clawDirection = direction;
        initialize();
    }

    public void initialize() {
        clawServo = Environment.getHardwareMap().get(Servo.class, servoName);
        clawServo.scaleRange(0.0, 1.0);
        close();
    }

    public void open() {
        stopped = false;
        targetPosition = clawDirection == ClawDirection.Left ? LEFT_OPEN_POSITION : RIGHT_OPEN_POSITION;
        clawServo.setPosition(targetPosition);
    }
    
    public void close() {
        stopped = false;
        targetPosition = clawDirection == ClawDirection.Left ? LEFT_CLOSED_POSITION : RIGHT_CLOSED_POSITION;
        clawServo.setPosition(targetPosition);
    }

    public void update() {
        if (stopped) {
            return;
        }
        
        double prevPosition = clawServo.getPosition();
        
        if (Math.abs(targetPosition - prevPosition) < 0.01) {
            stop(targetPosition);
        }
        else {
            // Detect if servo is making progress towrad target position
            clawServo.setPosition(targetPosition);
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e)
            {
                
            }
        
            // If we didn't move, stop the motor, we must be grabbing an object
            if (Math.abs(clawServo.getPosition() - prevPosition) < 0.005) {
                stop(prevPosition);
            }
        }
    }
    
    public void stop(double stopPosition) {
        stopped = true;
        targetPosition = stopPosition;
        clawServo.setPosition(stopPosition);
    }
        
    public double getPosition() {
        return clawServo.getPosition();
    }
    
    public String toString() {
        return String.format("%s claw: pos: %.2f (target: %.2f, stopped: %b)",
            clawDirection.name(),
            clawServo.getPosition(),
            targetPosition,
            stopped);
    }
}
