# pi-temperature
*Records and displays temperatures gathered from a Raspberry Pi through the use of [Vktech DS18b20](http://www.amazon.com/Vktech-DS18b20-Waterproof-Temperature-Transmitter/dp/B00CHEZ250/ref=sr_1_7?ie=UTF8&qid=1423584993&sr=8-7&keywords=raspberry+pi+temperature+sensor) temperature sensors.*

# Table of Contents
* **[Requirements](#requirements)**
* **[Installation](#installation)**
* **[Temperature Usage](#temperature-usage)**
 * **[Sensors](#sensors)**
   * **[REST Endpoints](#rest-endpoints-sensors)**
    * **[Overrides](#overrides)**
 * **[Alerts](#alerts)**
   * **[REST Endpoints](#rest-endpoints-alerts)**
    * **[CsvAlert](#csvalert)**

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

To check the installation and setup were successful, there is a `hello-world` REST endpoint to test with:
```curl <raspberry-pi-ip>:8080/hello-world```

## Temperature Usage
### Sensors
##### REST Endpoints Sensors

Endpoint | Method | Variables | Example | Description
--- | --- | --- | --- | ---
`/sensors/list` | GET | _None_ | `curl <raspberry-pi-ip>:8080/sensors/list` | Returns a list of all Sensors and their information - name, serialId, tempC, tempF.
`/sensors/list/<name>` | GET | _None_ | `curl <raspberry-pi-ip>:8080/sensors/list/<name>` | Returns the specified Sensor and its information - name, serialId, tempC, tempF.
`/sensors/name/<serialId>` | PUT | `<serialId>` | `curl -X PUT -d "Some name" <raspberry-pi-ip>:8080/sensors/name/<name>` | Updates the provided sensor with a user-friendly name.

##### Overrides

 - By default, sensors are read every minute and update the alerts that are currently set on. The time between sensor reads can be overridden by providing the `sensorScanSchedule` property in the `application.properties` file. The sensor scan schedules use the [Quartz Cron Expressions](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06). Example of overridding the schedule to read every 10 seconds:
```
sensorScanSchedule=0/10 * * * * ?
```
 - By default, sensors are read from the directory `/sys/bus/w1/devices/w1_bus_master1/`. The sensors directory can be overridden by providing the `sensorsMasterDirectory` property in the `application.properties` file. Example of overridding the sensors directory:
```
sensorsMasterDirectory=S:\\sensors\\
```

### Alerts
##### REST Endpoints Alerts

Endpoint | Method | Variables | Example | Description
--- | --- | --- | --- | ---
`/alerts/list` | GET | _None_ | `curl <raspberry-pi-ip>:8080/alerts/list` | Returns a list of all Alerts and their status (on or off).
`/alerts/list/<name>` | GET | _None_ | `curl <raspberry-pi-ip>:8080/alerts/list/<name>` | Returnsthe specific Alert and its status (on or off).
`/alerts/setOn/<name>` | PUT | `<name>` - The name of the alert. | `curl -X PUT <raspberry-pi-ip>:8080/alerts/setOn/<name>` | Turns the specified alert on.
`/alerts/setOff/<name>` | PUT | `<name>` - The name of the alert. | `curl -X PUT <raspberry-pi-ip>:8080/alerts/setOff/<name>` | Turns the specified alert off.
`/alerts/update/<name>` | PUT | `<name>` - The name of the alert. | `curl -X PUT -d "Some message" <raspberry-pi-ip>:8080/alerts/update/<name>` | Updates the specified alert with the provided message.

##### CsvAlert

This alert writes the sensor data to a csv file. The alert name is `CsvAlert` and the supported update information is:

Variable | Type | Default
--- | --- | ---
filename | String | `sensor-data.csv`
delimiter | char | `,`

An example of updating this alert to write to a file called `something.csv` with a delimiter of `@` is:
```
curl -X PUT -d {\"filename\":\"something.csv\",\"delimiter\":\"@\"} <raspberry-pi-ip>:8080/alerts/update/CsvAlert
```
