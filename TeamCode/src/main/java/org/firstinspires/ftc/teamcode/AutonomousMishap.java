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
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.doclint.Env;

@Autonomous(name="Mishap Autonomous", group="Linear Opmode")
public class AutonomousMishap extends LinearOpMode {

    // Autonomous mode enum
    private enum AutonomousMode {
        CRATER_MODE,
        DEPOT_MODE
    };

    private enum Direction {
        LEFT,
        RIGHT
    };

    // Declare OpMode members.
    private MishapBot mishapBot;
    private DigitalChannel autonomousSwitch;
    private AutonomousMode mode = AutonomousMode.CRATER_MODE;

    @Override
    public void runOpMode() {
        Environment.setHardwareMap(hardwareMap);
        Environment.setTelemetry(telemetry);
        Environment.setOpMode(this);

        autonomousSwitch = hardwareMap.get(DigitalChannel.class, "autonomous_switch");
        if (autonomousSwitch.getState()) {
            mode = AutonomousMode.DEPOT_MODE;
        }

        mishapBot = new MishapBot();

        telemetry.addData("AutonomousMode", "Initialized. Mode: %s", mode.name());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        switch(mode) {
            case CRATER_MODE:
                RunCraterMode();
                break;
            case DEPOT_MODE:
                // TODO: replace with RunDepotMode() when ready
                RunCraterMode();
                break;
        }
    }
    
    private void RunCraterMode()
    {
        // Lower the robot from lander
        //lowerBot();

        // Drive backwards to unhook from lander
        //drive(-1, -1, 0.2);

        // Turn away from lander with the back end, so turn left
        //turn(Direction.LEFT, 0.5, 3);

        // Drive backwards into the crater
        drive(1, 1, 4);

        // Turn towards depot
        //turn(Direction.RIGHT, 0.5, 2.0);

        // Drive forward to the depot
        //drive(1, 1, 3);

        // Open claws to drop marker
        //dropMarker();

        // Turn slightly to left to face crater with back
        //turn(Direction.LEFT, 0.5, 0.5);

        // Drive to crater backwards
        //drive(-1, -1, 3);
    }

    private void RunDepotMode()
    {
        // LowerBot();
        // Turn(90);
        // Drive(4);
        // Turn(-45);
        // Drive(-12);

    }

    private void lowerBot() {
        if (opModeIsActive()) {
            mishapBot.getHook().autoLowerBot(8);
        }
        telemetry.update();
    }

    private void dropMarker() {
        if (opModeIsActive()) {
            mishapBot.getArm().reset();
        }
        telemetry.update();
    }

    private void drive(double leftPower, double rightPower, double driveTimeInSeconds) {
        if (opModeIsActive()) {
            mishapBot.getDriveBase().drive(leftPower, rightPower, driveTimeInSeconds);
        }
        telemetry.update();
    }

    private void turn(Direction dir, double power, double turnTimeInSeconds) {
        if (opModeIsActive()) {
            if (dir == Direction.LEFT) {
                mishapBot.getDriveBase().turnLeft(power, turnTimeInSeconds);
            }
            else {
                mishapBot.getDriveBase().turnRight(power, turnTimeInSeconds);
            }
        }
        telemetry.update();
    }
}
