# rock-raper-scissors-game-back

Demo Game

There are two profiles for this project `dev` and `psql`. For sql profile, I used Postgresql database. For dev profile, I
used on memory H2 database.

Default profile is dev. You can switch the profile either from   

`application.properties -> spring.profiles.active=dev`

or from the command line:

`mvn clean spring-boot:run -Dspring-boot.run.profiles=dev`

Pleas run the backend before you execute frontend app. 
