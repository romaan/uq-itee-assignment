del /Q /F ..\bin\* 
javac ..\src\*.java -d ..\bin
start NameServer.bat
ping localhost -n 2 -w 10000 > null
start Bank.bat
ping localhost -n 2 -w 10000 > null
start AirlineQF.bat
ping localhost -n 2 -w 10000 > null
start AirlineVA.bat
ping localhost -n 2 -w 10000 > null
start ReservationService.bat
ping localhost -n 2 -w 1000 > null
start TravelAgentManual.bat
ping localhost -n 2 -w 10000 > null
start TravelAgentAutomatic.bat
REM PAUSE