/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

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
        if (gamepad1.left_trigger > 0.2) {
            mishapBot.getArm().lower();
        }
        else if (gamepad1.right_trigger > 0.2) {
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
        if (gamepad1.a) {
            mishapBot.getRightClaw().open();
        }
        else if (gamepad1.b) {
            mishapBot.getRightClaw().close();
        }
        else if (gamepad1.x) {
            mishapBot.getLeftClaw().open();
        }
        else if (gamepad1.y) {
            mishapBot.getLeftClaw().close();
        }
    }
}
