package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.trello.AttachmentsByBodyField;
import com.crud.tasks.domain.trello.BadgesField;
import com.crud.tasks.domain.trello.TrelloField;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloServiceTests {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void fetchTrelloBoardsTest() {
        // Given
        TrelloBoardDto trelloBoardA = new TrelloBoardDto("1", "BoardA", new ArrayList<>());
        TrelloBoardDto trelloBoardB = new TrelloBoardDto("2", "BoardB", new ArrayList<>());
        List<TrelloBoardDto> trelloBoards = Arrays.asList(trelloBoardA, trelloBoardB);

        // When
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        List<TrelloBoardDto> returnedBoards = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(2, returnedBoards.size());
        assertEquals("BoardB", returnedBoards.get(1).getName());
    }

    @Test
    public void createTrelloCardTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "none", "1", "2");
        TrelloField trelloField = new TrelloField(1, 1);
        AttachmentsByBodyField bodyField = new AttachmentsByBodyField(trelloField);
        BadgesField badgesField = new BadgesField(1, bodyField);
        CreatedTrelloCardDto testCardDto = new CreatedTrelloCardDto("1", "card", "tmp/tmp", badgesField);

        // When
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(testCardDto);
        when(adminConfig.getAdminMail()).thenReturn("none");
        CreatedTrelloCardDto createdCard = trelloService.createTrelloCard(trelloCardDto);
        BadgesField returnedBadges = createdCard.getBadges();
        AttachmentsByBodyField returnedAttachment = returnedBadges.getAttachments();

        // Then
        assertNotNull(createdCard);
        assertEquals("tmp/tmp", createdCard.getShortUrl());
        assertEquals(1, returnedBadges.getVotes());
        assertNotNull(returnedAttachment);
        assertEquals(1, returnedAttachment.getTrello().getCard());
        assertEquals(1, returnedAttachment.getTrello().getBoard());
    }

    @Test
    public void createTrelloCardWithNoBadgestTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "none", "1", "2");
        CreatedTrelloCardDto testCardDto = new CreatedTrelloCardDto("1", "card", "tmp/tmp", null);

        // When
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(testCardDto);
        when(adminConfig.getAdminMail()).thenReturn("none");
        CreatedTrelloCardDto createdCard = trelloService.createTrelloCard(trelloCardDto);
        BadgesField returnedBadges = createdCard.getBadges();

        // Then
        assertNotNull(createdCard);
        assertEquals("tmp/tmp", createdCard.getShortUrl());
        assertNull(returnedBadges);
    }

}
