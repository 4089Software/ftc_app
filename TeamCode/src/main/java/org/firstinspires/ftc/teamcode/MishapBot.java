package org.firstinspires.ftc.teamcode;

public class MishapBot {

    private Hook hook;
    private Arm arm;
    private DriveBase driveBase;
    private Claw leftClaw;
    private Claw rightClaw;

    public MishapBot()
    {
        hook = new Hook();
        arm = new Arm();
        driveBase = new DriveBase();
        leftClaw = new Claw("servo_left_claw", Claw.ClawDirection.Left);
        rightClaw = new Claw("servo_right_claw", Claw.ClawDirection.Right);

        Environment.setBot(this);
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

    public Claw getLeftClaw() { return leftClaw; }

    public Claw getRightClaw() { return rightClaw; }
    
    public void update() {
        leftClaw.update();
        rightClaw.update();
        
        Environment.getTelemetry().addData("ClawStates", "%s, %s",
                leftClaw.toString(),
                rightClaw.toString());
    }
}
