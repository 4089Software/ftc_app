package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Environment {
    private static Telemetry telemetry;
    private static HardwareMap hardwareMap;
    private static LinearOpMode opMode;
    private static MishapBot mishapBot;

    public static void setTelemetry(Telemetry t) {
        telemetry = t;
    }

    public static Telemetry getTelemetry() {
        return telemetry;
    }

    public static void setHardwareMap(HardwareMap h) {
        hardwareMap = h;
    }

    public static HardwareMap getHardwareMap() { return hardwareMap; }

    public static void setOpMode(LinearOpMode mode) { opMode = mode; }

    public static LinearOpMode getOpMode() { return opMode; }

    public static void log(String format, Object ... args) {
        telemetry.log().add(format, args);
    }

    public static void setBot(MishapBot bot) { mishapBot = bot; }

    public static MishapBot getBot() { return mishapBot; }
}
