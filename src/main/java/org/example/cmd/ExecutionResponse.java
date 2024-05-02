package org.example.cmd;

/**
 * Класс объекта-ответа. Описывает, как была выполнена команда: успешно или нет, а также содержит строку-пояснение
 */
public class ExecutionResponse {
    /**
     * Код ответа
     */
    private final boolean exitCode;
    /**
     * Описание ответа
     */
    private final String massage;

    /**
     * Конструктор - задаёт код объекта-ответа и его описание
     * @param code - код ответа
     * @param s - строка описание
     */
    public ExecutionResponse(boolean code, String s) {
        exitCode = code;
        massage = s;
    }

    /**
     * Конструктор - автоматически создает "успешный" объект-ответ
     * @param s - строка-состояние
     */
    public ExecutionResponse(String s) {
        this(true, s);
    }
    /**
     * Получить код объекта-ответа
     * @return код ответа
     */
    public boolean getExitCode() { return exitCode; }

    /**
     * Получить строку-описание объекта-ответа
     * @return строка-описание
     */
    public String getMassage() { return massage; }

    /**
     * Текстовое представление объекта-ответа команды
     * @return строка вида "(логический код ответа) сообщение"
     */
    public String toString() {
        return "(" + exitCode + ") " + massage;
    }
}

/**
 * Интерфейс для объектов, содержащих описание
 */
interface Describable {
    /**
     * Получить имя команды
     * @return имя команды
     */
    public String getName();

    /**
     * Получить текст описание команды
     * @return строка-описание
     */
    public String getDescription();
}

/**
 * Интерфейс для исполняемых объектов (консольных команд)
 */
interface Executable {
    /**
     * Метод выполняющий основной функционал объекта
     * @param userCommandArgument - аргумент команды (в консоли пишется сразу после имени команды)
     * @param isScript - была ли запущена команда из скрипта
     * @return Объект содержащий статус команды (true - успешно выполнена/ false - произошла ошибка
     * и текстовую строку о состоянии команды
     */
    public ExecutionResponse apply(String userCommandArgument, boolean isScript);
}