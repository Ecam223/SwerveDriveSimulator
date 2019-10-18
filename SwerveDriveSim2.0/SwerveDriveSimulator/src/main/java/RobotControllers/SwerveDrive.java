package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

public class SwerveDrive implements RobotController {
    @Override
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        double leftPower = joysticks.getLeftStick().y * 5.0;
        double rightPower = joysticks.getLeftStick().y * 5.0;
        double right = joysticks.getLeftStick().x * 5.0;
        double turn = joysticks.getRightStick().x * 5.0;

        // assist to allow driving exactly straight
        if(Math.abs(leftPower - rightPower) < 0.5) {
            leftPower = rightPower;
        }

        for(SwerveWheelInterface wheel : robot.getDrivetrain()) {
            // make sure the wheels are always facing forward (none of this funny swerve business)
            wheel.setWheelAngle(0);

            // if the wheel is on the right side of the robot
            //wheel angle is between 29.84 and 29.85
            if(wheel.getPosition().x > 0) {
                if(Math.abs(right / 5) > 0.5){
                    wheel.setWheelAngle(-29.845);
                    wheel.setWheelVelocity(-right);
                }else {
                    wheel.setWheelVelocity(leftPower);
                }
                if(Math.abs(turn) > 0.5){
                    wheel.setWheelVelocity(-turn);
                }
            }else{
                if(Math.abs(right / 5) > 0.5){
                    wheel.setWheelAngle(29.845);
                    wheel.setWheelVelocity(right);
                }else {
                    wheel.setWheelVelocity(leftPower);
                }
                if(Math.abs(turn) > 0.5){
                    wheel.setWheelVelocity(turn);
                }
            }
        }
    }
}
