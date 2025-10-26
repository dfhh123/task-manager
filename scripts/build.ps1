$ErrorActionPreference = "Stop"
Set-Location "$PSScriptRoot\.."

Write-Host "Building services..." -ForegroundColor Cyan
.\gradlew.bat clean build -x test
docker-compose build --parallel

Write-Host "`nBuild complete. Run: docker-compose up -d" -ForegroundColor Green

