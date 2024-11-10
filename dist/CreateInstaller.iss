[Setup]
AppName=Grantbrowse
AppVersion=1.0.0
DefaultDirName={userappdata}\Grantbrowse
DefaultGroupName=Grantbrowse
OutputDir="C:\Users\floormann\Desktop\Projects\Grantbrowse\dist\1.0.0\installer"
OutputBaseFilename=GrantbrowseSetup
DisableProgramGroupPage=yes
DisableDirPage=yes
DisableReadyMemo=yes
SetupIconFile="C:\Users\floormann\Desktop\Projects\Grantbrowse\dist\icons\icon.ico"

[Tasks]
Name: "desktopicon"; Description: "Create a desktop icon"; GroupDescription: "Additional tasks"; Flags: unchecked
Name: "startmenu"; Description: "Create a Start Menu shortcut"; GroupDescription: "Additional tasks"; Flags: unchecked

[Files]
Source: "C:\Users\floormann\Desktop\Projects\Grantbrowse\dist\1.0.0\exe\Grantbrowse v1.0.0.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\floormann\Desktop\Projects\Grantbrowse\dist\1.0.0\exe\*.*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Grantbrowse"; Filename: "{app}\Grantbrowse v1.0.0.exe"; Tasks: startmenu
Name: "{userdesktop}\Grantbrowse"; Filename: "{app}\Grantbrowse v1.0.0.exe"; Tasks: desktopicon

[Run]
Filename: "{app}\Grantbrowse v1.0.0.exe"; Description: "{cm:LaunchProgram,Grantbrowse}"; Flags: nowait postinstall skipifsilent
