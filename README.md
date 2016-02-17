# MacEnvTest

### How to get things done

Use next steps: 

1. chmod +x ./build.sh && ./build.sh
2. Open Finder and run application
3. Depend on to your shell(bash, zsh) please open any of these files: .zshrc, .bashrc, .profile, .bash_profile
  * place there line `launchctl setenv YOUR_VAR WHATEVER`
  * reload profile settings with `source ~/.yourscenariofile`
4. Use form to specify necessary variable name and get the result :)

### Caveates

There are several places where you can set environment variables.

~/.profile: use this for variables you want to set in all programs launched from the terminal (note that, unlike on Linux, all shells opened in Terminal.app are login shells).
~/.bashrc: this is invoked for shells which are not login shells. Use this for aliases and other things which need to be redefined in subshells, not for environment variables that are inherited.
/etc/profile: this is loaded before ~/.profile, but is otherwise equivalent. Use it when you want the variable to apply to terminal programs launched by all users on the machine (assuming they use bash).
~/.MacOSX/environment.plist: this is read by loginwindow on login. It applies to all applications, including GUI ones, except those launched by Spotlight in 10.5 (not 10.6). It requires you to logout and login again for changes to take effect. This file is no longer supported as of OS X 10.8.
your user's launchd instance: this applies to all programs launched by the user, GUI and CLI. You can apply changes at any time by using the setenv command in launchctl. In theory, you should be able to put setenv commands in ~/.launchd.conf, and launchd would read them automatically when the user logs in, but in practice support for this file was never implemented. Instead, you can use another mechanism to execute a script at login, and have that script call launchctl to set up the launchd environment.
/etc/launchd.conf: this is read by launchd when the system starts up and when a user logs in. They affect every single process on the system, because launchd is the root process. To apply changes to the running root launchd you can pipe the commands into sudo launchctl.
The fundamental things to understand are:

environment variables are inherited by a process's children at the time they are forked.
the root process is a launchd instance, and there is also a separate launchd instance per user session.
launchd allows you to change its current environment variables using launchctl; the updated variables are then inherited by all new processes it forks from then on.
Example of setting an environment variable with launchd:

echo setenv REPLACE_WITH_VAR REPLACE_WITH_VALUE | launchctl
Now, launch your GUI app that uses the variable, and voila!

To work around the fact that ~/.launchd.conf does not work, you can put the following script in ~/Library/LaunchAgents/local.launchd.conf.plist:

`<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
  <key>Label</key>
  <string>local.launchd.conf</string>
  <key>ProgramArguments</key>
  <array>
    <string>sh</string>
    <string>-c</string>
    <string>launchctl < ~/.launchd.conf</string>    
  </array>
  <key>RunAtLoad</key>
  <true/>
</dict>
</plist>`
Then you can put setenv REPLACE_WITH_VAR REPLACE_WITH_VALUE inside ~/.launchd.conf, and it will be executed at each login.

Note that, when piping a command list into launchctl in this fashion, you will not be able to set environment variables with values containing spaces. If you need to do so, you can call launchctl as follows: launchctl setenv MYVARIABLE "QUOTE THE STRING".

Also note that other programs that run at login may execute before the launchagent, and thus may not see the environment variables it sets.






