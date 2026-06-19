package ru.javarush.textadventure.model;

public enum GameChoice {
    ACCEPT("Принять вызов"),
    REJECT("Отклонить вызов"),
    INQUIRE("Сначала спросить, чего они хотят"),
    GO_TO_BRIDGE("Подняться на мостик"),
    REFUSE_BRIDGE("Отказаться подниматься на мостик"),
    REQUEST_ESCORT("Попросить escort до мостика"),
    PROCEED_WITH_ESCORT("Идти к мостику под охраной"),
    REFUSE_ESCORT("Отказаться от escort"),
    TELL_TRUTH("Рассказать правду о себе"),
    LIE("Солгать о себе"),
    STAY_SILENT("Промолчать и ждать");

    private final String label;

    GameChoice(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
