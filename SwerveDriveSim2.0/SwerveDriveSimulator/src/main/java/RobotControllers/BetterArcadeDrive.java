package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

public class BetterArcadeDrive implements RobotController {
    @Override
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        double leftPower = joysticks.getLeftStick().y * 5.0;
        double rightPower = joysticks.getLeftStick().y * 5.0;
        double right = joysticks.getRightStick().x * 5.0;
        double c1 = 2 * 3.14 * 0.5;
        double c2 = 2 * 3.14 * 0.75;

        // assist to allow driving exactly straight
        if (Math.abs(leftPower - rightPower) < 0.5) {
            leftPower = rightPower;
        }

        for (SwerveWheelInterface wheel : robot.getDrivetrain()) {
            // make sure the wheels are always facing forward (none of this funny swerve business)
            wheel.setWheelAngle(0);


            if (wheel.getPosition().x > 0) {
                //If wheel is on right side of robot
                if (wheel.getPosition().x > 0.51) {
                    wheel.setWheelVelocity(-((c2 - c1) * right + leftPower));
                } else if (wheel.getPosition().x > 0 && wheel.getPosition().x < .49) {
                    wheel.setWheelVelocity(leftPower);
                }
            } else if (wheel.getPosition().x < 0) {
                if (wheel.getPosition().x < -0.51) {
                    wheel.setWheelVelocity((c2 - c1) * right + leftPower);
                } else if (wheel.getPosition().x < 0 && wheel.getPosition().x >-0.49) {
                    wheel.setWheelVelocity(-leftPower);
                }
            }
        }
    }
}
