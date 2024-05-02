package org.example;

import org.example.interfaces.Console;

/**
 * Класс позволяющий перевести пользовательский ввод в поле объекта и при этом обработать все возможные
 * некорректные вводы
 */
public class SmartInputParser {
    /**
     * ввод -> строка
     * @param console - консоль
     * @param fieldName - имя требуемого поля
     * @param lowerLengthLimit - нижнее ограничение по длине
     * @param isRec - из скрипта?
     * @return строчное поле
     * @throws Ask.AskBreak
     */
    public static String parseToString(Console console, String fieldName, int lowerLengthLimit, boolean isRec) throws Ask.AskBreak {
        String value;
        while (true) {
            if(!isRec)console.print(fieldName + ": ");
            value = console.readln().trim();
            if (!value.isEmpty()) {
                if (value.equals("exit")) throw new Ask.AskBreak();
                else if (value.equals("null")) console.printWarning("Не может быть null");
                else if (value.length() >= lowerLengthLimit) break;
                else {
                    if(!isRec)console.printWarning("Введите строку длиной " + lowerLengthLimit + " или более");
                }
            }
            else {
                if(!isRec)console.printWarning("Введите непустое значение");
            }
        }
        return value;
    }

    /**
     * @see SmartInputParser#parseToString(Console, String, int, boolean)
     * Тоже самое, но без ограничения по длине (непустое)
     * @param console - консоль
     * @param fieldName - имя требуемого поля
     * @param isRec - из скрипта?
     * @return строчное поле
     * @throws Ask.AskBreak
     */
    public static String parseToString(Console console, String fieldName, boolean isRec) throws Ask.AskBreak {
        return parseToString(console, fieldName, 1, isRec);
    }

    /**
     * ввод -> Long
     * @param console - консоль
     * @param fieldName - имя требуемого поля
     * @param moreThanZero - больше нуля?
     * @param isRec - из скрипта?
     * @return поле
     * @throws Ask.AskBreak
     */
    public static Long parseToLong(Console console, String fieldName, boolean moreThanZero, boolean isRec) throws Ask.AskBreak {
        String value;
        Long answer;
        while (true){
            if(!isRec)console.print(fieldName + ": ");
            value = console.readln().trim();
            if (!value.isEmpty()) {
                if(value.equals("exit")) throw new Ask.AskBreak();
                else if (value.equals("null")) console.printWarning("Не может быть null");
                else
                    try {
                        answer = Long.parseLong(value);
                        if (moreThanZero){
                            if (answer > 0) break;
                            else {
                                if(!isRec)console.printWarning("Введите число больше 0");
                            }
                        }else break;
                    }
                    catch(NumberFormatException e) {
                        if(!isRec)console.printWarning("Число неверного формата");
                    }
            } else {
                if(!isRec)console.printWarning("Введите непустое значение");
            }
        }
        return answer;
    }


}
