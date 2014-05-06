start cmd.exe /C usertwolocation.bat
ping 1.1.1.1 -n 1 -w 2000 > nul
start cmd.exe /C usertwoBPHeart.bat
ping 1.1.1.1 -n 1 -w 2000 > nul
REM "Starting User Interface"
start cmd.exe /C usertwoUI.bat
PAUSE