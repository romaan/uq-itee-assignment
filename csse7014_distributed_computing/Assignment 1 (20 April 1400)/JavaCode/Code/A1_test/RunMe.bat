REM "Starting the Avis Router"
cd substartup

start cmd.exe /C avis.bat
REM ping 1.1.1.1 -n 1 -w 2000 > nul

start cmd.exe /C compile.bat
ping 1.1.1.1 -n 1 -w 3000 > nul
PAUSE

start cmd.exe /C execute.bat
ping 1.1.1.1 -n 1 -w 2000 > nul