package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTests {

    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void validateCardTest() {
        // Given
        TrelloCard trelloCardA = new TrelloCard("New Card", "Card for test", "2", "1");
        TrelloCard trelloCardB = new TrelloCard("test Card", "Card for test", "2", "1");

        // When
        trelloValidator.validateCard(trelloCardA);
        trelloValidator.validateCard(trelloCardB);

        // Then
    }

    @Test
    public void validateTrelloBoardsTests() {
        // Given
        TrelloBoard trelloBoardA = new TrelloBoard("1", "New board", new ArrayList<>());
        TrelloBoard trelloBoardB = new TrelloBoard("2", "TEST", new ArrayList<>());
        List<TrelloBoard> trelloBoards = Arrays.asList(trelloBoardA, trelloBoardB);

        // When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        // Then
        assertEquals(2, trelloBoards.size());
        assertEquals(1, filteredBoards.size());
    }
}
