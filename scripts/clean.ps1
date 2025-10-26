$ErrorActionPreference = "Stop"
Set-Location "$PSScriptRoot\.."

$confirmation = Read-Host "Delete all containers, images and volumes? (y/N)"
if ($confirmation -ne 'y' -and $confirmation -ne 'Y') { exit 0 }

docker-compose down -v
docker images --format "{{.ID}}" | Select-String "super-pet" | ForEach-Object { docker rmi -f $_ }
docker volume prune -f
docker network prune -f

Write-Host "Cleanup complete" -ForegroundColor Green

