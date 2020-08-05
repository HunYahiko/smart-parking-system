This is the Arduino section.

First, you must have Arduino IDE installed on your PC/laptop.

To view the code, open the .ino file in Arduino IDE.

Since this application was developed on Arduino Nano 3.0, in Boards you must select Arduino Nano board, so you can compile the application.
If you want to upload the code into physical microcontroller, you must connect your Arduino Nano into the laptop via USB cable. Once it is connected, check in the Device Manager
which COM port is used for the communication, and select that port in Arduino IDE. Now you can upload your code.

You can check the electrical scheme at ...

So, what does this device do? Since this device represents a parking spots, it receives messages from server with a certain function to execute. The main functions are
giving the server a feedback about parking spot status and blocking the spot. The device has 3 LEDs, which represent the state of the spot. Green means FREE, red means OCCUPIED
and yellow means booked.
The device checks the status of the spot via a ultrasonic sensor. If there is an obstacle, it means there is a car parked, otherwise it is free.
