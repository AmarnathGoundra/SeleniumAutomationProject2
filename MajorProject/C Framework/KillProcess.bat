@echo off

title Killing Processes

taskkill /F /IM chrome.exe
taskkill /F /IM chromedriver.exe
taskkill /F /IM iexplorer.exe


echo closed all processes as requested...

pause