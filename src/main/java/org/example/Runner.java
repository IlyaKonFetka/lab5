package org.example;


import org.example.cmd.Command;
import org.example.cmd.ExecutionResponse;
import org.example.interfaces.Console;

import java.util.*;

/**
 * Класс запускающий в себе интерактивный режим - бесконечный цикл приёма команд из консоли/файла-скрипта их обработки
 * и вывода результата
 */
public class Runner {
    /**
     * Класс консоли
     */
    private Console console;
    /**
     * Менеджер команд
     */
    private CommandManager commandManager;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Метод реализующий основной функционал Runner
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandStatus;
            String userCommandName;
            String userCommandArgument;

            while (true) {
                console.prompt();
                userCommandName = console.read().trim();
                userCommandArgument = console.readln().trim();

                commandStatus = launchCommand(userCommandName, userCommandArgument);
                if (commandStatus.getMassage().equals("exit")) break;

                if(commandStatus.getExitCode()) console.printSuccessful(commandStatus.getMassage());
                else console.printError(commandStatus.getMassage());
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Подготовка команды для дальнейшего выполнения
     * @param userCommandName - имя выполняемой команды
     * @param userCommandArgument - строка приписных аргументов команды
     * @return
     */
    private ExecutionResponse launchCommand(String userCommandName, String userCommandArgument) {
        if (userCommandName.isEmpty()) return new ExecutionResponse("");
        Command command = commandManager.getCommands().get(userCommandName);

        if (command == null) return new ExecutionResponse(false, "Команда '" + userCommandName
                + "' не найдена. Наберите 'help' для справки");
        commandManager.addToHistory(userCommandName);
        return command.apply(userCommandArgument, commandManager.isScript());
    }
}