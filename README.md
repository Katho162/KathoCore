<div align="center">
  <img src="https://i.imgur.com/MKqkXDN.png"><br>
  <p>Just a simple Bukkit plugin for handle some Minecraft Servers problem.</p>
</div>

Index
=================
* [About](#about)
* [Installation](#instalation)
* [Configuration](#configuration)
* [Commands](#commands)
* [TODO](#todo)
* [Thanks](#thanks)

About
=================
KathoCore was born into 2018 for solve some personal problems in servers with my friends. But today i work in it to improve my Java knowledge and implement some cool things.

Installation
=================
You can compile the plugin from scratch using [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven](http://maven.apache.org). if you have them now you can just put that command in your command line:
```
$ mvn package
```
But if you want use it as dependencie of another project you can use:
```
$ mvn install
```

Configuration
=================
The plugin supports multilanguage configuration. if you want to change the plugin messages or maybe implement a new language you can change it in `config.json` file in the plugin folder:
```json
{
  "pluginLanguage": "en_US"
}
```
But you can't forget to create the file containing the name of your language in the same folder...
If you have put your file name and have made the file now you can copy the content of `en_US.json` to starting your translation...
**And if you can translate the plugin to new languages, please send a new pull request!**

Commands
=================
* [Register](#register)
* [Login](#login)

Register
-----------------

The register command first, iniates the player in the server, using: `/register <password> <password>`.
After that the plugin will make a new player account file in `accounts/playeruuid.json`
And there you can find the player account info...

Login
-----------------

The login command is the log in the player in the server, using: `/login <password>`. but it works by an intelligent way, if player has the same IP of last login and it isn't 24h later than last login, the plugin will automaticly log in the player in the server.

TODO
=================
- [X] Make a Register and Login intelligent system.
- [ ] Make a Chest protection intelligent system.
- [ ] Make a secure trading system.
- [ ] Implement a mini cryptocurrency as ecconomy.
- [ ] Implement concurrency. 
- [ ] Implement a system to toggle plugin funcionalities.

Thanks
=================
- Hypnos thanks for helping with the Spanish es_ES translation.
- Yannick thanks for helping with the German de_DE translation.
- bignavigator thanks for helping with the Russian ru_RU translation.
- Esperanto2018 thanks for helping with the Czech cz_CZ and the Russian ru_RU translation.
