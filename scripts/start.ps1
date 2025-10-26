$ErrorActionPreference = "Stop"
Set-Location "$PSScriptRoot\.."

Write-Host "Starting services..." -ForegroundColor Cyan
docker-compose up -d

Write-Host "`nServices started:" -ForegroundColor Green
Write-Host "  Frontend:         http://localhost:3000"
Write-Host "  API Gateway:      http://localhost:8080"
Write-Host "  Keycloak:         http://localhost:8180"
Write-Host "  Eureka:           http://localhost:8761"
Write-Host "  MailHog:          http://localhost:8025"
Write-Host "  Schema Registry:  http://localhost:8081"

