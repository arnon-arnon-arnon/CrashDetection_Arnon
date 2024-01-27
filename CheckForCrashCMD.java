// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SensorsSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class CheckForCrashCMD extends Command {
  private final SensorsSubsystem sensors;

  public CheckForCrashCMD(SensorsSubsystem sensors) {
    this.sensors = sensors;
    addRequirements(sensors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  // i have scheduled it as the the defualt command of the schedueler!

  //the command checks each sensors if it is exceeding it's set max, and if so:
  //sets the crashWarning flag(boolean) to true, and adds to the dashboardMessage string
  //a description of which sensor flagged a crash, and it's current data

  @Override
  public void execute() {
    String dashboardMessage="Status: ";
    boolean crashWarning=false;

    //checks the acceleration
    if(sensors.getAcceleration()>OperatorConstants.acceleration_MAX){
      crashWarning=true;
      dashboardMessage+="Acceleration Exceeded Max("+sensors.getAcceleration()+" currently)\n";
    }
    //checks the RPM(rotations per minute, of the wheel)
    if(sensors.getEncoderRPM()>OperatorConstants.encoder_RevoltionssPerMinute_MAX){
      crashWarning=true;
      dashboardMessage+="RPM Exceeded Max ("+sensors.getEncoderRPM()+" currently\n";
    }
    
    //checks the gyro tilt forwards & backwards: first forwards, |IF NOT|, then backwards (negative, so <)
    if(sensors.getGyroXPitch()>OperatorConstants.gyro_xPitch_tiltForward_MAX){
      crashWarning=true;
      dashboardMessage+="Angle of Tilt Forwards Exceeded Max ("+sensors.getGyroXPitch()+" currently)\n";
    }
    else
       if(sensors.getGyroXPitch()<OperatorConstants.gyro_xPitch_tiltBackward_MAX){
        crashWarning=true;
        dashboardMessage+="Angle of Tilt Backwards Exceeded Max ("+sensors.getGyroXPitch()+" currently)\n";
       }

    //checks the gyro tilt to the sides: first to the right, |IF NOT|, then to the left (negative, so <)
    if(sensors.getGyroYRoll()>OperatorConstants.gyro_yRoll_tiltRight_MAX){
      crashWarning=true;
      dashboardMessage+="Angle of Tilt to the Right Exceeded Max ("+sensors.getGyroYRoll()+" currently)\n";
    }
    else
        if(sensors.getGyroYRoll()<OperatorConstants.gyro_yRoll_tiltRight_MAX){
          crashWarning=true;
          dashboardMessage+="Angle of Tilt to the Left Exceeded Max ("+sensors.getGyroYRoll()+" currently)\n";
        }
    
    //after checking and collecting sensor data, its time to send the data to the shuffleborad_smartboard:
    //if a sensors is a crash-risk, the crashWarning bool is true, and the sensor data is sent
    //if no sensors were a crash-risk, we simply send "all good!" to the shuffleboard_smartboard

    //i can just add to the string and check if its been altered, bypassing a bool entirely, that would be
    //quicker and maybe cleaner code but less understandable

    if(crashWarning==true){
      SmartDashboard.putBoolean(dashboardMessage, crashWarning);
    }
    else{
      dashboardMessage+= "all good!";
      SmartDashboard.putBoolean(dashboardMessage,false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
