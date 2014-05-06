cd ..\..\src
title Compile
SET CLASSPATH=.;..\lib\avis-client.jar
del ..\bin\*.class
javac HomeManager.java -d ..\bin
javac Sensor.java -d ..\bin
javac SmartHomeUI.java -d ..\bin
PAUSE