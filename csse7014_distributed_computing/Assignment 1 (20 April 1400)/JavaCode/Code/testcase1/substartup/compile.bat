cd ..\..\src
title Compile
SET CLASSPATH=.;..\lib\avis-client.jar
del ..\bin\*.class
ping 1.1.1.1 -n 1 -w 2000 > nul
javac HomeManager.java -d ..\bin
javac Sensor.java -d ..\bin
javac SmartHomeUI.java -d ..\bin
PAUSE