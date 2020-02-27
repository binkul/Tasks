call runcrud.bat
if "%ERRORLEVEL%" == "0" goto firefox
echo.
echo Error with program compiling
goto end

:firefox
start "crud" "%PROGRAMFILES%\Mozilla Firefox\firefox.exe" "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto finish
echo.
echo cannot run web browser
goto end

:finish
echo.
echo firefox is running

:end
