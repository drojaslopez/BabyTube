@rem Script de arranque de Gradle Wrapper para Windows
@echo off
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set wrapperJar=%DIRNAME%gradle/wrapper/gradle-wrapper.jar

if exist "%wrapperJar%" (
    java -jar "%wrapperJar%" %*
) else (
    echo Error: No se encontro gradle-wrapper.jar en gradle/wrapper/
)