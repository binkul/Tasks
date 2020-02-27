call runcrud.bat
if "%ERRORLEVEL%" == "0" goto firefox
echo.
echo Error with program compiling
goto end

:firefox
@rem Windows 10 or Windows 7
if exist "%PROGRAMFILES%\Mozilla Firefox\firefox.exe" (
    start "crud" "%PROGRAMFILES%\Mozilla Firefox\firefox.exe" "http://localhost:8080/crud/v1/task/getTasks"
    if "%ERRORLEVEL%" == "0" goto finish
    echo.
    echo cannot run web browser
    goto end
) else (
    start "crud" "%PROGRAMFILES% (x86)\Mozilla Firefox\firefox.exe" "http://localhost:8080/crud/v1/task/getTasks"
    if "%ERRORLEVEL%" == "0" goto finish
    echo.
    echo cannot run web browser
    goto end
)


:finish
echo.
echo firefox is running

:end
