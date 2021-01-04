package org.firstinspires.ftc.teamcode.Autonomous.OpModes.AutoSupers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BuildSuperOp;


public abstract class BlueTrianglePark extends BuildSuperOp {
    public BUILDSTATUS status = BUILDSTATUS.FLIPPER;

    @Override
    public void loop() {
        startPoint = 1;
        //declare telemetry for all motors/servos
        //this allows us to see how the motors are behaving in the code
        //and then compare it to how they perform in real life
        telemetry.addData("Status: ", status);

        //switch statements for changing the status of the robot
        //this allows us to use different code for each status
        //there are methods created below the switch statement for easier reading
        switch (status) {
            case FLIPPER:
                park();
                status = BUILDSTATUS.PARK;
                break;
            case PARK:
                if(accelDrive.isEmpty){
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
