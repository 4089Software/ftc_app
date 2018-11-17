package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class MishapBot {
    
    private DcMotor backl = null;
    private DcMotor frontl = null;
    private DcMotor backr = null;
    private DcMotor frontr = null;
    private DcMotor topc = null;
    private Servo spin = null;
    
    public void initialize()
    {
        frontr = hardwareMap.get(DcMotor.class, "front_right");
        frontr.setDirection(DcMotor.Direction.REVERSE);

        frontl = hardwareMap.get(DcMotor.class, "front_left");
        frontl.setDirection(DcMotor.Direction.FORWARD);

        backr = hardwareMap.get(DcMotor.class, "back_right");
        backr.setDirection(DcMotor.Direction.REVERSE);

        backl = hardwareMap.get(DcMotor.class, "back_left");
        backl.setDirection(DcMotor.Direction.FORWARD);

        topc = hardwareMap.get(DcMotor.class, "top_claw");
        topc.setDirection(DcMotor.Direction.FORWARD);

        spin = hardwareMap.get(Servo.class, "front_spin");
        spin.setDirection(Servo.Direction.FORWARD);
    }
    
}
