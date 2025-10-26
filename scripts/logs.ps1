param([string]$ServiceName)
$ErrorActionPreference = "Stop"
Set-Location "$PSScriptRoot\.."

if ([string]::IsNullOrEmpty($ServiceName)) {
    Write-Host "Available services:"
    docker-compose ps --services
    Write-Host "`nUsage: .\scripts\logs.ps1 <service-name>"
    exit 0
}

docker-compose logs -f $ServiceName

