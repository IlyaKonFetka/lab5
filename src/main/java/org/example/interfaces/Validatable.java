package org.example.interfaces;

/**
 * Интерфейс дял объектов, подлежащих валидации (проверки корректности состояния)
 */
public interface Validatable {
    /**
     * Проверить корректность состояния
     * @return вердикт
     */
    boolean validate();
}
