package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo clawServo;
    private String servoName;

    public Claw(String name) {
        servoName = name;
        initialize();
    }

    public void initialize() {
        clawServo = Environment.getHardwareMap().get(Servo.class, servoName);
    }

    public void open() {
        if (clawServo.getPosition() < Servo.MAX_POSITION) {
            clawServo.setPosition(Servo.MAX_POSITION);
        }
    }

    public void close() {
        if (clawServo.getPosition() > Servo.MIN_POSITION) {
            clawServo.setPosition(Servo.MIN_POSITION);
        }
    }
}
