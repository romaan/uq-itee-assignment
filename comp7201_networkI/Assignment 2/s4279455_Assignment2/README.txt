Assumptions:

* Make sure all the processes are started. 
* First the name server is started, then Bank, AirlineVA and AirlineQF, ReservationServer and TravelAgentAutomatic and TravelAgentManual is started
* The IP and Port addresses are cached by every process after contacting NameServer. Hence we assume one or more processes are not restarted.
* Java 1.7 compiler is used.

Help:

* The code has been tested throughly on Windows Operating System and screenshot is attached for your kind reference.
* There is no script to automate the start of the processes or entire system, however there are bat file that help run the processes easily on windows operating system, but not Moss.
* Compile.bat will compile all files and Launch will launch all the processes on Windows. We need to execute equivalent commands on Moss environment to make it work.
* However, it will run fine on Moss, but we need run the bash commands from different terminal and give commands seperately. (javac and java, assuming compiler 1.7)


* Every process uses a single port (As per the specification)
* The core UDP is implemented in UDPCommTravelAgency.java and ReRunTravelAgency.
* Reliablity is implemented using timeouts and timestamps as sequence numbers. The sequence numbers are cached at receiver side to ignore duplicate packet while the whole message is cached at the sender untill ACK packet is received from the receiver.
* Hence we simulate three conditions (1) Normal UDP packet sent (2) Packet not sent, which in other words means just waiting for ACK assumping packet is lost hence will be re-sent later. (3) Sending duplicate packets. This can be seen in UDPCommTravelAgency::sendUDP() 
* Sequence number is combination of current time stamp + 3 digit random number because there are cases where only time stamp leads to wrongly infer duplicate packets, as the system is very fast to send two different message with same time stamp, hence 3 digit random number makes sequence number truely unique.