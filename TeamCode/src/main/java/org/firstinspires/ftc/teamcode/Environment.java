package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Environment {
    private static Telemetry telemetry;
    private static HardwareMap hardwareMap;

    public static void setTelemetry(Telemetry t) {
        telemetry = t;
    }

    public static Telemetry getTelemetry() {
        return telemetry;
    }

    public static void setHardwareMap(HardwareMap h) {
        hardwareMap = h;
    }

    public static HardwareMap getHardwareMap() {
        return hardwareMap;
    }
}
