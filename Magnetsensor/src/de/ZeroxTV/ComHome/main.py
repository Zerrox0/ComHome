import RPi.GPIO as GPIO
import MySQLdb
import time
GPIO.setmode(GPIO.BCM)
#VAR
db = MySQLdb.connect("192.168.2.102", "data", "123456", "comHome")
cursor = db.cursor()
sensors = [1,2]
ports= {
    1: 17, 2: 27}
#SETUP
GPIO.setup(1, GPIO.OUT)
GPIO.output(1,1)
GPIO.setup(17, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(27, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
#CODE
#FALLS 17 TRUE
last = {
    1: GPIO.input(ports[1]), 2: GPIO.input(ports[2])}
while True:
    #SENS 1
    for sensor in sensors:
        current = GPIO.input(ports[sensor])
        if current != last[sensor]:
            #cursor.execute("INSERT INTO Data "
            #                    "(Zeit, Wert, SensorID) "
            #                    "VALUES ('" + time.strftime('%Y-%m-%d %H:%M:%S') + "', '" + str(current) + "', '" + str(sensor) +"')")
            cursor.execute("INSERT INTO Data "
                                "(Zeit, Wert, SensorID) "
                                "VALUES (%s, %s, %s)", (time.strftime('%Y-%m-%d %H:%M:%S'), current, sensor))
            print("Written in to DB")
            db.commit()
            last[sensor] = current
            print(current)
