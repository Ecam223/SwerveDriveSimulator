package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

public class BetterArcadeDrive implements RobotController {
    @Override
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        //Variables
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

            if(wheel.getPosition().x > 0){
                //If wheel is on right side of robot
                if(wheel.getPosition().x > 0.51){
                    //If wheel is on the outside of the right side
                    if((right/5) > 0.001){
                        //If right stick has moved
                        if((leftPower/5) < -0.001) {
                            wheel.setWheelVelocity(leftPower + ((c2 - c1) * (right)));
                        }else if((leftPower/5) > 0.001){
                            wheel.setWheelVelocity(leftPower - ((c2 - c1) * (right)));
                        }
                    }else if((right/5) >=0 && (right/5) < 0.001){
                        //If right stick is in neutral position
                        wheel.setWheelVelocity(leftPower);
                    }
                }else if(wheel.getPosition().x > 0 && wheel.getPosition().x < 0.51) {
                    //If wheel is on the inside of the right side
                    wheel.setWheelVelocity(leftPower);
                }
            }else if(wheel.getPosition().x < 0){
                //If wheel is on left side of roboto
                if(wheel.getPosition().x < -.51){
                    //If wheel is on outside of the left side
                    if((right/5) < -0.001){
                        //If right stick has moved
                        if((leftPower/5) < -0.001) {
                            wheel.setWheelVelocity(leftPower - ((c2 - c1) * (right)));
                        }else if((leftPower/5) > 0.001){
                            wheel.setWheelVelocity(leftPower + ((c2 - c1) * (right)));
                        }
                    }else if((right / 5) <= 0 && (right / 5) >= -0.001){
                        wheel.setWheelVelocity(leftPower);
                    }
                }else if(wheel.getPosition().x < 0 && wheel.getPosition().x > -.51){
                    //If wheel is on inside of left side
                    wheel.setWheelVelocity(leftPower);
                }
            }
            if((leftPower / 5) < 0.001 && (leftPower / 5) > -.001){
                if(wheel.getPosition().x > 0) {
                    if(right > 0) {
                        wheel.setWheelVelocity(-right);
                        System.out.println("It worked");
                    }else if(right < 0){
                        wheel.setWheelVelocity(-right);
                        System.out.println("It worked 2.0");
                    }
                }else if(wheel.getPosition().x < 0){
                    if(right > 0){
                        wheel.setWheelVelocity(right);
                    }else if(right < 0){
                        wheel.setWheelVelocity(right);
                    }
                }
            }
        }
    }
}
