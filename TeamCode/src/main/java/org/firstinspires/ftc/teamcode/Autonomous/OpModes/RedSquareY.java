package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.PlayerSuperOp;

@Autonomous
public class RedSquareY extends RedSquare {
    @Override
    public void init() {
        super.init();
        parkWall = true;
        parkPos = 1;
    }
}

