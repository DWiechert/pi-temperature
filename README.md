# pi-temperature
*Records and displays temperatures gathered from a Raspberry Pi. Also has some led functionality.*

## Table of Contents
* **[Requirements](#requirements)**
* **[Installation](#installation)**
* **[Temperature Usage](#temperature-usage)**

### Requirements
* JDK 7
* Maven 3

### Installation
1. Checkout the project:
```git clone https://github.com/DWiechert/pi-temperature.git```
2. Build the project:
```mvn clean install -Dmaven.test.skip```
3. SCP the built war `<project-directory>/target/pi-temperature-<version>.war` onto your Raspberry Pi.
4. Start the war:
```sudo java -jar pi-temperature-<version>.war```

To check the installation and setup were successful, there is a `hello-world` REST endpoint to test with.
```curl <raspberry-pi-ip>:8080/hello-world```

### Temperature Usage
Coming soon.
Follow temperature sheet - http://www.adafruit.com/datasheets/DS18B20.pdf

```
curl localhost:8080/alerts/list
curl -X PUT localhost:8080/alerts/setOff/CopyOfNoOpAlert
curl -X PUT localhost:8080/alerts/setOn/CopyOfNoOpAlert
```