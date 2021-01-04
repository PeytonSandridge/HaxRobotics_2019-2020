package org.firstinspires.ftc.teamcode.Autonomous.OpModes.AutoSupers;

import org.firstinspires.ftc.teamcode.Autonomous.BuildSuperOp;

public abstract class RedTriangle extends BuildSuperOp {

    //This uses an enum declared in SuperOp
    //It declares the first STATUS as "START"
    private BUILDSTATUS status = BUILDSTATUS.FLIPPER;
    //create new stopwatch

    public void init(){
        super.init();
        startPoint = -1;
    }

    @Override
    public void loop() {
        parkPos = 1;
        //declare telemetry for all motors/servos
        //this allows us to see how the motors are behaving in the code
        //and then compare it to how they perform in real life
        telemetry.addData("LatchMotor Position: ", LatchMotor.getCurrentPosition());
        telemetry.addData("Front Right: ", FrontRightDrive.getCurrentPosition());
        telemetry.addData("Back Left: ", BackLeftDrive.getCurrentPosition());
        telemetry.addData("Back Right: ", BackRightDrive.getCurrentPosition());
        telemetry.addData("Front Left: ", FrontLeftDrive.getCurrentPosition());
        telemetry.addData("Status: ", status);

        //switch statements for changing the status of the robot
        //this allows us to use different code for each status
        //there are methods created below the switch statement for easier reading
        switch (status) {
            case FLIPPER:
                toFoundation();
                status = BUILDSTATUS.TOFOUNDATION;
                break;
            case TOFOUNDATION:
                if(accelDrive.isEmpty) {
                    LatchMotor.setPower(0.3);
                    sleep_secs(0.5);
                    LatchMotor.setPower(0);
                    drag();
                    status = BUILDSTATUS.DRAG;
                } else {
                    updateAndDrive();
                }
                break;
            case DRAG:
                if(accelDrive.isEmpty) {
                    LatchMotor.setPower(-0.3);
                    sleep_secs(0.5);
                    LatchMotor.setPower(0);
                    around();
                    status = BUILDSTATUS.AROUND;
                } else {
                    updateAndDrive();
                }
                break;
            case AROUND:
                if(accelDrive.isEmpty) {
                    park();
                    status = BUILDSTATUS.PARK;
                } else {
                    updateAndDrive();
                }
                break;
            case PARK:
                if(accelDrive.isEmpty) {
                    status = BUILDSTATUS.STOP;
                } else {
                    updateAndDrive();
                }
                break;
            case STOP:
                stop1();
                break;
        }
    }
}