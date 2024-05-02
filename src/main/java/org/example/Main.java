package org.example;

import org.example.cmd.*;

import java.text.ParseException;


public class Main {
    public static void main(String[] args) {
        //check
        var standardConsole = new StandardConsole();
        if (args.length == 0) {
            standardConsole.println("Введите имя загружаемого файла, как аргумент командной строки");
            System.exit(1);
        }
        var serializationManager = new SerializationManager(args[0],standardConsole);
        var collectionManager = new CollectionManager(serializationManager);

        if (!collectionManager.loadCollection())
            standardConsole.printWarning("Коллекция пуста");
        else{
            standardConsole.printSuccessful("Коллекция успешно загружена из файла");
        }collectionManager.setLastInitTime();

        var commandManager = new CommandManager();
        commandManager.register("add", new Add(standardConsole, collectionManager));
        commandManager.register("add_if_max", new AddIfMax(collectionManager,standardConsole));
        commandManager.register("clear", new Clear(collectionManager));
        commandManager.register("execute_script", new ExecuteScript(standardConsole, commandManager));
        commandManager.register("exit", new Exit());
        commandManager.register("filter_contains_passport_i_d",new FilterContainsPassportID(collectionManager,standardConsole));
        commandManager.register("help", new Help(commandManager, standardConsole));
        commandManager.register("history", new History(commandManager,standardConsole));
        commandManager.register("info",new Info(collectionManager,standardConsole));
        commandManager.register("min_by_weight", new MinByWeight(collectionManager,standardConsole));
        commandManager.register("print_ascending", new PrintAscending(collectionManager,standardConsole));
        commandManager.register("remove_by_id",new RemoveByID(collectionManager));
        commandManager.register("remove_lower", new RemoveLower(collectionManager,standardConsole));
        commandManager.register("save",new Save(collectionManager));
        commandManager.register("show",new Show(collectionManager,standardConsole));
        commandManager.register("update",new Update(collectionManager,standardConsole));
        new Runner(standardConsole, commandManager).interactiveMode();

    }
}