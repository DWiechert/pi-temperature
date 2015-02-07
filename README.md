# pi-temperature
*Records and displays temperatures gathered from a Raspberry Pi. Also has some led functionality.*

# Table of Contents
* **[Requirements](#requirements)**
* **[Installation](#installation)**
* **[Temperature Usage](#temperature-usage)**

## Requirements
* JDK 7
* Maven 3

## Installation
1. Wire up the temperature sensors to your Raspberry Pi and run the following commands:
```
sudo modprobe w1-gpio
sudo modprobe w1-therm
```
2. Checkout the project:
```git clone https://github.com/DWiechert/pi-temperature.git```
3. Build the project:
```mvn clean install -Dmaven.test.skip```
4. SCP the built war `<project-directory>/target/pi-temperature-<version>.war` onto your Raspberry Pi.
5. Start the war:
```sudo java -jar pi-temperature-<version>.war```

To check the installation and setup were successful, there is a `hello-world` REST endpoint to test with.
```curl <raspberry-pi-ip>:8080/hello-world```

## Temperature Usage
### Alerts
##### REST Endpoints

Endpoint | Method | Variables | Example | Description
--- | --- | --- | --- | ---
`/list` | GET | _None_ | `curl <raspberry-pi-ip>:8080/alerts/list` | Returns a list of all Alerts and their status (on or off).
`/setOn/<name>` | PUT | `<name>` - The name of the alert. | `curl -X PUT <raspberry-pi-ip>:8080/alerts/setOn/<name>` | Turns the specified alert on.
`/setOff/<name>` | PUT | `<name>` - The name of the alert. | `curl -X PUT <raspberry-pi-ip>:8080/alerts/setOff/<name>` | Turns the specified alert off.
`/update/<name>` | PUT | `<name>` - The name of the alert. `<message>` - The update message for the alert. | `curl -X PUT -d message="Some message" <raspberry-pi-ip>:8080/alerts/update/<name>` | Updates the specified alert with the provided message.

### Sensors
##### REST Endpoints

Endpoint | Method | Variables | Example | Description
--- | --- | --- | --- | ---
`/list` | GET | _None_ | `curl <raspberry-pi-ip>:8080/sensors/list` | Returns a list of all Sensors and their information - name, serialId, tempC, tempF.

##### Overrides

By default, sensors are read every minute and update the alerts that are currently set on. The time between sensor reads can be overridden by providing the `sensorScanSchedule` property in the `application.properties` file. The sensor scan schedules use the [Quartz Cron Expressions](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06).

Example of overridding the schedule to read every 10 seconds:
```
sensorScanSchedule=0/10 * * * * ?
```
