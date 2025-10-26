$ErrorActionPreference = "Stop"
Set-Location "$PSScriptRoot\.."

docker-compose down
Write-Host "Services stopped" -ForegroundColor Green

