@echo off
@set SCRIPT_DIR=%~dp0

set SBT_OPTS=-Xms1024M -Xmx1024M -Xss1M
set SBT_OPTS=%SBT_OPTS% -XX:MaxPermSize=200M -XX:ReservedCodeCacheSize=60M
set SBT_OPTS=%SBT_OPTS% -XX:+CMSClassUnloadingEnabled -XX:-UseGCOverheadLimit
set SBT_OPTS=%SBT_OPTS% -Dsbt.ivy.home="%SCRIPT_DIR%repository"
set SBT_OPTS=%SBT_OPTS% -Dsbt.boot.directory="%SCRIPT_DIR%boot"

set SBT_JAR="%SCRIPT_DIR%sbt-launch.jar"

java %JAVA_OPTS% %SBT_OPTS% -jar %SBT_JAR% %*
