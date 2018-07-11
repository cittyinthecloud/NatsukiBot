# NatsukiBot
A bot for DDMC, designed with the goal of being easy to extend upon.

Features:
- $iam command, with self-assignable roles set by an admin
- $noiam command, which toggles whether or not the iam command can be used
- $gulag and $ungulag commands. The gulag command is also timed, and will give back the roles the user had before they were gulaged.
- Users will be reassigned to roles they were in when they left the server. No more dodging gulag, or having to ask mods for your Development state roles back after a hiatus.
- $comedy-dark command. It functions identically to how Sayori does.
- DM on join, telling users to read #introduction to learn how to get into the server.
- #server_suggestions management. It even gets the arrows in the right order every time!
- Role restores and gulag states are written to disk, so even if the bot has to go down, these are saved.
