REM "Starting Home Manager"
start cmd.exe /C homemanager.bat
ping 1.1.1.1 -n 1 -w 2000 > nul

REM "Starting Clock Sensor"
start cmd.exe /C clock.bat
ping 1.1.1.1 -n 1 -w 2000 > nul

REM "Starting Temperature Sensor"
start cmd.exe /C temperature.bat
ping 1.1.1.1 -n 1 -w 2000 > nul

REM "User Location Sensor"
start cmd.exe /C useronelocation.bat
ping 1.1.1.1 -n 1 -w 2000 > nul

REM "User One BP Heart"
start cmd.exe /C useroneBPHeart.bat
ping 1.1.1.1 -n 1 -w 2000 > nul

REM "Starting User Interface"
start cmd.exe /C useroneUI.bat
ping 1.1.1.1 -n 1 -w 2000 > nul