<#
.SYNOPSIS
This will start a console application
#>
$ps1_file = $MyInvocation.MyCommand.Path
$base_dir = split-path (split-path $ps1_file)

$port = 50000 + @(ipconfig|select-string IPv4|?{$_ -match "\d+\.\d+\.\d+\.(\d+)"}|%{$matches[1]})[0]

$OFS = ";"
$class_path = "$base_dir;$(ls $base_dir\lib\*.jar)"

$argList = @" 
-server -showversion
-Djava.net.preferIPv4Stack=true
-Dtangosol.coherence.distributed.localstorage=true
-Dtangosol.coherence.cluster=$($Env:COMPUTERNAME)
-Dtangosol.coherence.clusterport=$port
-cp $class_path
com.tangosol.net.CacheFactory
"@ -replace "`r*`n"," "

start java $argList -NoNewWindow

# vim: set ts=4 sw=4 et:
