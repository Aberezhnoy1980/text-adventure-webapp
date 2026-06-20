package ru.javarush.textadventure.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.javarush.textadventure.model.GameChoice;
import ru.javarush.textadventure.model.GameResult;
import ru.javarush.textadventure.model.GameSession;
import ru.javarush.textadventure.model.GameState;
import ru.javarush.textadventure.model.GameStep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameEngineTest {

    private GameEngine gameEngine;
    private GameSession session;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
        session = new GameSession();
        session.setPlayerName("Alex");
    }

    @Test
    void classicWinPath_leadsToVictory() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.GO_TO_BRIDGE);
        gameEngine.applyChoice(session, GameChoice.TELL_TRUTH);

        assertEquals(GameState.VICTORY, session.getCurrentState());
        assertEquals(GameResult.VICTORY, session.getGameResult());
        assertEquals("Тебя вернули домой. Победа.", session.getOutcomeMessage());
        assertTrue(gameEngine.isGameFinished(session));
    }

    @Test
    void rejectAtStart_leadsToDefeat() {
        gameEngine.applyChoice(session, GameChoice.REJECT);

        assertEquals(GameState.DEFEAT, session.getCurrentState());
        assertEquals(GameResult.DEFEAT, session.getGameResult());
        assertEquals("Ты отклонил вызов. Поражение.", session.getOutcomeMessage());
    }

    @Test
    void refuseBridge_leadsToDefeat() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.REFUSE_BRIDGE);

        assertEquals(GameState.DEFEAT, session.getCurrentState());
        assertEquals("Ты не пошёл на переговоры. Поражение.", session.getOutcomeMessage());
    }

    @Test
    void lieOnBridge_leadsToDefeat() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.GO_TO_BRIDGE);
        gameEngine.applyChoice(session, GameChoice.LIE);

        assertEquals(GameState.DEFEAT, session.getCurrentState());
        assertEquals("Твою ложь разоблачили. Поражение.", session.getOutcomeMessage());
    }

    @Test
    void staySilentOnBridge_leadsToDefeat() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.GO_TO_BRIDGE);
        gameEngine.applyChoice(session, GameChoice.STAY_SILENT);

        assertEquals(GameState.DEFEAT, session.getCurrentState());
        assertEquals("Тишину сочли за отказ от сотрудничества. Поражение.", session.getOutcomeMessage());
    }

    @Test
    void inquireBranch_canStillWin() {
        gameEngine.applyChoice(session, GameChoice.INQUIRE);
        assertEquals(GameState.INQUIRY, session.getCurrentState());

        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.GO_TO_BRIDGE);
        gameEngine.applyChoice(session, GameChoice.TELL_TRUTH);

        assertEquals(GameResult.VICTORY, session.getGameResult());
    }

    @Test
    void escortBranch_reachesBridgeWithEscortFlag() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.REQUEST_ESCORT);
        assertEquals(GameState.ESCORT_APPROACH, session.getCurrentState());

        gameEngine.applyChoice(session, GameChoice.PROCEED_WITH_ESCORT);

        assertEquals(GameState.ON_BRIDGE, session.getCurrentState());
        assertTrue(session.isEscortArrived());
        assertTrue(gameEngine.getCurrentStep(session).getQuestion().contains("Escort"));
    }

    @Test
    void refuseEscort_leadsToDefeat() {
        gameEngine.applyChoice(session, GameChoice.ACCEPT);
        gameEngine.applyChoice(session, GameChoice.REQUEST_ESCORT);
        gameEngine.applyChoice(session, GameChoice.REFUSE_ESCORT);

        assertEquals(GameResult.DEFEAT, session.getGameResult());
        assertEquals("Отказ от escort воспринят как враждебность. Поражение.", session.getOutcomeMessage());
    }

    @Test
    void startStep_containsBaselineAndExtraChoices() {
        GameStep step = gameEngine.getCurrentStep(session);

        assertEquals("Ты потерял память? Принять вызов НЛО?", step.getQuestion());
        assertEquals(3, step.getOptions().size());
    }

    @Test
    void getCurrentStep_requiresSession() {
        assertThrows(NullPointerException.class, () -> gameEngine.getCurrentStep(null));
    }

    @Test
    void applyInvalidChoice_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> gameEngine.applyChoice(session, GameChoice.LIE));
    }

    @Test
    void applyChoiceAfterFinish_throwsException() {
        gameEngine.applyChoice(session, GameChoice.REJECT);

        assertThrows(IllegalStateException.class, () -> gameEngine.applyChoice(session, GameChoice.ACCEPT));
    }

    @ParameterizedTest
    @EnumSource(value = GameChoice.class, names = {"ACCEPT", "REJECT", "INQUIRE"})
    void submitChoice_acceptsValidStartChoices(GameChoice choice) {
        assertNull(gameEngine.submitChoice(session, choice.name()));
    }

    @Test
    void submitChoice_returnsErrorForBlankValue() {
        assertEquals("Выберите вариант ответа.", gameEngine.submitChoice(session, null));
        assertEquals("Выберите вариант ответа.", gameEngine.submitChoice(session, "   "));
    }

    @Test
    void submitChoice_returnsErrorForUnknownValue() {
        assertEquals("Выберите вариант ответа.", gameEngine.submitChoice(session, "UNKNOWN"));
    }

    @Test
    void submitChoice_returnsErrorForChoiceInvalidInCurrentState() {
        assertEquals("Выберите вариант ответа.", gameEngine.submitChoice(session, GameChoice.LIE.name()));
    }

    @Test
    void submitChoice_appliesValidChoice() {
        assertNull(gameEngine.submitChoice(session, GameChoice.ACCEPT.name()));
        assertEquals(GameState.ACCEPTED_CHALLENGE, session.getCurrentState());
    }

    @Test
    void resetForNewGame_restoresInitialState() {
        gameEngine.applyChoice(session, GameChoice.REJECT);
        session.setGamesPlayed(3);

        session.resetForNewGame();

        assertEquals(GameState.START, session.getCurrentState());
        assertEquals(GameResult.IN_PROGRESS, session.getGameResult());
        assertFalse(session.isEscortArrived());
        assertFalse(gameEngine.isGameFinished(session));
        assertEquals(3, session.getGamesPlayed());
    }
}
