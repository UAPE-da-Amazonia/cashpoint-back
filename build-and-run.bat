@echo off
echo Buildando a aplicacao localmente...
call gradlew build -x spotlessJavaCheck -x spotlessKotlinCheck -x spotlessKotlinGradleCheck

if %ERRORLEVEL% NEQ 0 (
    echo Erro no build local!
    pause
    exit /b 1
)

echo Build local concluido! Subindo Docker Compose...
docker-compose up --build -d

echo Pronto! A aplicacao deve estar rodando em http://localhost:8082
pause 