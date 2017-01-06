/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a legacy (NXT-compatible) Hitechnic Color Sensor v2.
 * It assumes that the color sensor is configured with a name of "sensor_color".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "Sensors", group = "Sigma6710")
//@Disabled
public class SensorTestSigma2016 extends LinearOpMode {

    ColorSensor beacon_color = null;  // Hardware Device Object
    ColorSensor lineLightSensor = null;
    ColorSensor front_light = null;
    ColorSensor back_light = null;

    UltrasonicSensor ultra_front = null;
    UltrasonicSensor ultra_back = null;

    ModernRoboticsI2cGyro gyro = null;

    @Override
    public void runOpMode() {


        // get a reference to our ColorSensor object.
        beacon_color = hardwareMap.colorSensor.get("beacon_color");
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        beacon_color.enableLed(false);

        front_light = hardwareMap.colorSensor.get("front_light");
        front_light.enableLed(true);

        back_light = hardwareMap.colorSensor.get("back_light");
        back_light.enableLed(true);

        lineLightSensor = hardwareMap.colorSensor.get("line_light");
        lineLightSensor.enableLed(true);

        // ultrasonic sensor
        ultra_back = hardwareMap.ultrasonicSensor.get("ultra_back");
        ultra_front = hardwareMap.ultrasonicSensor.get("ultra_front");

        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        // make sure the gyro is calibrated before continuing
        while (!isStopRequested() && gyro.isCalibrating()) {
            sleep(50);
            idle();
        }

        gyro.resetZAxisIntegrator();

        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();

        // wait for the start button to be pressed.
        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Beacon: R:G:B ", "%d:%d:%d", beacon_color.red(), beacon_color.green(), beacon_color.blue());
            telemetry.addData("front: ", "%d", front_light.red() + front_light.green() + front_light.blue());
            telemetry.addData("back: ", "%d", back_light.red() + back_light.green() + back_light.blue());
            telemetry.addData("middle: ", "%d", lineLightSensor.red() + lineLightSensor.green() + lineLightSensor.blue());
            telemetry.addData("u_front: ", "%f", ultra_front.getUltrasonicLevel());
            telemetry.addData("u_back: ", "%f", ultra_back.getUltrasonicLevel());
            telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());

            telemetry.update();
        }
    }
}
