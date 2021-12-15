@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
echo 1:%1 >> %~dpn0.log
echo 2:%2 >> %~dpn0.log
echo 3:%3 >> %~dpn0.log
echo 4:%4 >> %~dpn0.log
echo 5:%5 >> %~dpn0.log
echo 6:%6 >> %~dpn0.log
echo 7:%7 >> %~dpn0.log
set J=
set T1=0
for /F "usebackq delims=" %%A in (`dir /B/S "C:\Program Files\Java\javadoc.exe"`) do (
	set T2=%%A
	set "T2=!T2:C:\program files\Java\jre=!"
	set "T2=!T2:C:\program files\Java\jdk=!"
	if "!T2:~0,3!"=="1.8" (
		if !T2! GTR !T1! (
			set "T1=!T2!"
			set "JD=%%A"
		)
	)
)
if "%JD%"=="" (
	for /F "usebackq delims=" %%A in (`dir /B/S "C:\Program Files (x86)\Java\javadoc.exe"`) do (
		set T2=%%A
		set "T2=!T2:C:\program files\Java\jre=!"
		set "T2=!T2:C:\program files\Java\jdk=!"
		if "!T2:~0,3!"=="1.8" (
			if !T2! GTR !T1! (
				set "T1=!T2!"
				set "JD=%%A"
			)
		)
	)
)
if "%JD%"=="" (
	echo ERROR: have you installed the latest JDK 8?
	set ERR=1
	goto :finished
)

for /D %%A in ("C:\Program Files (x86)\Graphviz*.*" "C:\Program Files\Graphviz*.*") do if exist "%%A\bin" set "GVBIN=%%A\bin"
if "%GVBIN%"=="" (
	echo ERROR: please install GraphViz. See notes on https://pyspace.org
	set ERR=1
	goto :finished
)
PATH "%GVBIN%";%PATH%
if exist %2/.classpath set "CP=-classpath @%2/.classpath"
"%JD%" %CP% -doclet org.umlgraph.doclet.UmlGraph -docletpath "%~dp0UMLGraph-5.7_2.32-SNAPSHOT\lib\UmlGraph.jar" -encoding UTF-8 -private -sourcepath %1;./views -subpackages "." -views -all -inferdep -inferrel -collpackages java.util.* -view All -outputencoding UTF-8 -d %2 %3 %4 %5 %6
for /R %2 %%A in (*.dot) do dot -Temf -o%%~dpnA.emf %%A
set ERR=0
:finished
endlocal
exit /B %ERR%
