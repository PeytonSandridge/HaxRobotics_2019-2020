package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.SuperOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class BlueBuild extends SuperOp {

    //This uses an enum declared in SuperOp
    //It declares the first STATUS as "START"
    private STATUS status = STATUS.START;

    //create new stopwatch
    private ElapsedTime time = new ElapsedTime();
    private double targetTime;

    private int currPosition;
    private int startPosition;
    private int targetPosition;
    private int targetPosition1;

    @Override
    public void loop() {

        //declare telemetry for all motors/servos
        //this allows us to see how the motors are behaving in the code
        //and then compare it to how they perform in real life
        telemetry.addData("LatchMotor Position: ", LatchMotor.getCurrentPosition());
        telemetry.addData("Time: ", time.seconds());
        telemetry.addData("Front Right: ", FrontRightDrive.getCurrentPosition());
        telemetry.addData("Back Left: ", BackLeftDrive.getCurrentPosition());
        telemetry.addData("Back Right: ", BackRightDrive.getCurrentPosition());
        telemetry.addData("Front Left: ", FrontLeftDrive.getCurrentPosition());
        telemetry.addData("Status: ", status);
        telemetry.addData("Latch Position: ", Latch.getPosition());

        currPosition = LatchMotor.getCurrentPosition();
        startPosition = LatchMotor.getCurrentPosition();
        targetPosition = currPosition - 500;
        targetPosition1 = startPosition;
        //switch statements for changing the status of the robot
        //this allows us to use different code for each status
        //there are methods created below the switch statement for easier reading
        switch (status) {
             case START:
                start1();
                break;
            case TOBLOCK:
                toBlock();
                break;
            case APPROACH:
                //time.reset();
                approach();
                break;
            case GETBLOCK:
                //time.reset();
                getBlock();
                status = STATUS.AWAY;
                break;
            case AWAY:
                //time.reset();
                away();
                break;
            case TOBUILD:
                //time.reset();
                toBuild();
                break;
            case PARK:
                //time.reset();
                park();
                break;
            /* case STOP:
                stop1();
                break; */
        }
    }

    //this is the first method run
    //it resets the elapsed time
    //then switches the status to 'TOBLOCK'
    private void start1(){
        //targetTime = .5;
        time.reset();
        status = STATUS.TOBLOCK;
    }

    //method to go to block
    //moves forward for 3 seconds at a motor power of .5
    //if the time is >= 3 seconds, the STATUS changes to 'APPROACH'
    private void toBlock() {
        //move forward for 3 seconds
        leftSpeedMultiplier = 1;
        targetTime = 2.5;
        drive(0, 0.5, 0);

        // vision code
        // if skystone is sighted
        /*
        */

        // set movement values to go towards block
        if(time.seconds()-targetTime > 0) {
            //stop
            drive(0,0,0);
            sleep_secs(0.5);
            //switch STATUS
            time.reset();
            status = STATUS.APPROACH;
        }
    }

    //runs for 1.5 seconds
    //strafes left for 1.5 seconds with .5 motor power
    //if time >= 1.5 seconds, the robot stops
    //and switches the STATUS to 'GETBLOCK'
    private void approach() {
        //time.reset();
        targetTime = 1.5;
        drive(0.5, 0, 0);
        telemetry.addData("Status: ", status);

        if(time.seconds() >= targetTime) {
            //robot stops for .5 secs
            drive(0,0,0);
            sleep_secs(0.5);
            //switches STATUS
            //resets clock
            //time.reset();
            status = STATUS.GETBLOCK;
        }
    }

    //rotate the arm down
    //check to see if the arm is in position
    //pull the block in and switch STATUS to 'AWAY'
     private void getBlock() {
        //rotate arm down
        LatchMotor.setPower(0.3);
        //check if the arm is in position
        if (currPosition <= targetPosition + 13 && currPosition >= targetPosition - 6) {
            //pull the block in
            LatchMotor.setPower(0);
            //switches STATUS
            //resets clock
            //time.reset();
            //status = STATUS.AWAY;
        }
    }

    //strafe left for 1.5 seconds
    //then stop and switch STATUS to 'AWAY'
    private void away() {
        //strafe left
        leftSpeedMultiplier = 1;
        targetTime = 1.5;
        drive(-0.5, 0, 0);
        if(time.seconds() >= targetTime) {
            //stop
            drive(0,0,0);
            sleep_secs(0.5);
            //switch STATUS
            //resets clock
            //time.reset();
            status = STATUS.TOBUILD;
        }
    }

    //go to build site and place the block back down
    //go backwards for 3 seconds
    //then release the LatchMotor and consequently, the block
    //check if the arm is up
    //lastly, set STATUS to 'PARK'
    private void toBuild() {
        //sets target position for grabber

        //methods to get the robot back to the build site to place down the block
        targetTime = 3;
        drive(0,0.5,0);
        telemetry.addData("Status: ", status);

        if(time.seconds() >= targetTime) {
            drive(0, 0, 0);
            //rotate the arm up
            currPosition = LatchMotor.getCurrentPosition();
            LatchMotor.setPower(0.3);
            //check if the arm is in position
            if (currPosition <= targetPosition1 + 13 && currPosition >= targetPosition1 - 13) {
                //pull the block in
                LatchMotor.setPower(0);
                Latch.setPosition(0);
                //switch STATUS
                status = STATUS.PARK;
                //resets clock
                //time.reset();
            }
        }
    }

    //park the robot in the middle of the alliance bridge
    //drive 1.5 seconds forward, then stop
    //switch STATUS to 'STOP'
    private void park() {
        // vision code to park the robot under the bridge
        //t_drive(0, -1, 0, 1);
        targetTime = 1.5;
        drive(0, -0.5, 0);

        if(time.seconds() >= targetTime){
            //stop robot
            drive(0,0,0);
            sleep_secs(0.5);
            //switch STATUS
            //status = STATUS.STOP;
        }
    }

    //stop all motion of the robot
    //set all motor powers to 0
    /* private void stop1(){
        drive(0,0,0);
    } */
}






/*
Are you ready, kids?
I cant hear youuuuuu

Who lives in a pineapple under the sea?
SPONGEBOB SQUAREPANTS!
Absorbent and yellow and porous is he
SPONGEBOB SQUAREPANTS!

If nautical nonsense be something you wish
SPONGEBOB SQUAREPANTS!
Then drop on the deck and flop like a fish
SPONGEBOB SQUAREPANTS!

SPONGEBOB SQUAREPANTS!
SPONGEBOB SQUAREPANTS!
SPONGEBOB SQUAREPANTS!
SPONGEBOB SQUAREPANTS!
 */