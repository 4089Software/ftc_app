package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic Mishap", group="Linear Opmode")

public class BasicMishap extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private MishapBot mishapBot;

    @Override
    public void runOpMode() {
        Environment.setHardwareMap(hardwareMap);
        Environment.setTelemetry(telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        mishapBot = new MishapBot();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            processArmInput();
            processHookInput();
            processDriveBaseInput();
            processClawInput();

            mishapBot.update();
            
            // Show the elapsed time
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    private void processDriveBaseInput() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower = Math.abs(gamepad1.left_stick_y) > 0.2 ? -gamepad1.left_stick_y : 0.0;
        double rightPower = Math.abs(gamepad1.right_stick_y) > 0.2 ? -gamepad1.right_stick_y : 0.0;

        // Send calculated power to wheels
        mishapBot.getDriveBase().drive(leftPower, rightPower);
    }

    private void processArmInput() {
        if (gamepad2.left_trigger > 0.0) {
            mishapBot.getArm().lower();
        }
        else if (gamepad2.right_trigger > 0.0) {
            mishapBot.getArm().raise();
        }
        else {
            mishapBot.getArm().stop();
        }
    }

    private void processHookInput() {
        if (gamepad1.dpad_up) {
            mishapBot.getHook().raise();
        }
        else if (gamepad1.dpad_down) {
            mishapBot.getHook().lower();
        }
        else {
            mishapBot.getHook().stop();
        }
    }

    private void processClawInput() {
        // Left claw: negative x opens claw, positive closes it
        double left_claw_dir = gamepad2.left_stick_x;
        
        // Right claw: positive x opens claw, negative closes it
        double right_claw_dir = gamepad2.right_stick_x;
        
        Claw leftClaw = mishapBot.getLeftClaw();
        Claw rightClaw = mishapBot.getRightClaw();
        
        if (left_claw_dir < -0.1) {
            leftClaw.open();
        }
        else if (left_claw_dir > 0.1) {
            leftClaw.close();
        }
        
        if (right_claw_dir < -0.1) {
            rightClaw.close();
        }
        else if (right_claw_dir > 0.1) {
            rightClaw.open();
        }
    }
}
