## Getting Started

A Simple CLI-Based Chatting app built through Java. Follow the Folder Structure in order to use it.

## Preview

<img src="CLI-Chat-App.gif" height="500" width="750">

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `src/server.java`: the server file for the chat app
- `src/app.java`: the client/application of the chat app
- `bin/.env`: This is what you have to create in the directory and put your SQL Server credentials here in the parameter of `host`,`name`,`pass`,`db`
Ex:
`host=localhost`
`name=root`
`pass=root`
`db=nodb`

Meanwhile, the compiled output files will be generated in the `bin` folder.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

1. Java Project Management: The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
