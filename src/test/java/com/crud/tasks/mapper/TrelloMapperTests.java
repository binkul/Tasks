package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloMapperTests {
    private final static Logger logger = LoggerFactory.getLogger(TrelloMapperTests.class);

    @InjectMocks
    TrelloMapper trelloMapper;

    @Test
    public void mapTrelloCardToDtoTest() {
        // Given
        TrelloCard trelloCard = new TrelloCard("Empty", "Empty card test", "none", "2.8");

        // When
        logger.info("Test mapTrelloCardToDtoTest start ...");
        TrelloCardDto trelloCardDto = trelloMapper.mapToTrelloCardDto(trelloCard);

        // Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    public void mapTrelloCardDtoToTrelloTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Empty", "Empty card test", "none", "2.8");

        // When
        logger.info("Test mapTrelloCardDtoToTrelloTest start ...");
        TrelloCard trelloCard = trelloMapper.mapToTrelloCard(trelloCardDto);

        // Then
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }

    @Test
    public void mapTrelloListToListDtoTest() {
        // Given
        List<TrelloList> trelloList = IntStream.rangeClosed(1, 20)
                .mapToObj(n -> new TrelloList(String.valueOf(n), "name" + n, false))
                .collect(Collectors.toList());

        // When
        logger.info("Test mapTrelloListToListDtoTest start ...");
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloList);

        // Then
        assertEquals(20, trelloListDtos.size());
        assertEquals("10", trelloListDtos.get(9).getId());
        assertEquals("name20", trelloListDtos.get(19).getName());
    }

    @Test
    public void mapTrelloListDtoToListTest() {
        // Given
        List<TrelloListDto> trelloListDto = IntStream.rangeClosed(0, 15)
                .mapToObj(n -> new TrelloListDto(String.valueOf(n), "name" + n, false))
                .collect(Collectors.toList());

        // When
        logger.info("Test mapTrelloListDtoToListTest start ...");
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);

        // Then
        assertEquals(16, trelloLists.size());
        assertEquals("7", trelloLists.get(7).getId());
        assertEquals("name15", trelloLists.get(15).getName());
    }

    @Test
    public void mapTrelloBoardToDtoTest() {
        // Given
        List<TrelloList> trelloListA = IntStream.rangeClosed(1, 10)
                .mapToObj(n -> new TrelloList(String.valueOf(n), "name" + n, false))
                .collect(Collectors.toList());
        List<TrelloList> trelloListB = IntStream.rangeClosed(1, 15)
                .mapToObj(n -> new TrelloList(String.valueOf(n), "list" + n, false))
                .collect(Collectors.toList());
        TrelloBoard trelloBoardA = new TrelloBoard("1", "BoardA", trelloListA);
        TrelloBoard trelloBoardB = new TrelloBoard("2", "BoardB", trelloListB);
        List<TrelloBoard> trelloBoards = Arrays.asList(trelloBoardA, trelloBoardB);

        // When
        logger.info("Test mapTrelloBoardToDtoTest start ...");
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        TrelloBoardDto boardA = trelloBoardDtos.get(0);
        TrelloBoardDto boardB = trelloBoardDtos.get(1);

        // Then
        assertEquals(2, trelloBoardDtos.size());
        assertEquals(10, boardA.getLists().size());
        assertEquals(15, boardB.getLists().size());
        assertEquals("name6", boardA.getLists().get(5).getName());
        assertEquals("list15", boardB.getLists().get(14).getName());
    }

    @Test
    public void mapTrelloBoardDtoToBoardTest() {
        // Given
        List<TrelloListDto> trelloListA = IntStream.rangeClosed(0, 13)
                .mapToObj(n -> new TrelloListDto(String.valueOf(n), "name" + n, false))
                .collect(Collectors.toList());
        List<TrelloListDto> trelloListB = IntStream.rangeClosed(0, 18)
                .mapToObj(n -> new TrelloListDto(String.valueOf(n), "list" + n, false))
                .collect(Collectors.toList());
        TrelloBoardDto trelloBoardDtoA = new TrelloBoardDto("1", "BoardA", trelloListA);
        TrelloBoardDto trelloBoardDtoB = new TrelloBoardDto("2", "BoardB", trelloListB);
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(trelloBoardDtoA, trelloBoardDtoB);

        // When
        logger.info("Test mapTrelloBoardDtoToBoardTest start ...");
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        TrelloBoard boardA = trelloBoards.get(0);
        TrelloBoard boardB = trelloBoards.get(1);

        // Then
        assertEquals(2, trelloBoards.size());
        assertEquals(14, boardA.getLists().size());
        assertEquals(19, boardB.getLists().size());
        assertEquals("name13", boardA.getLists().get(13).getName());
        assertEquals("list17", boardB.getLists().get(17).getName());
    }
}
