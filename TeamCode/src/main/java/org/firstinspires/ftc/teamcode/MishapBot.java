package org.firstinspires.ftc.teamcode;

public class MishapBot {

    private Hook hook;
    private Arm arm;
    private DriveBase driveBase;

    public MishapBot()
    {
        hook = new Hook();
        arm = new Arm();
        driveBase = new DriveBase();
    }

    public Hook getHook() {
        return hook;
    }

    public Arm getArm() {
        return arm;
    }

    public DriveBase getDriveBase() {
        return driveBase;
    }
}
