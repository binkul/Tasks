package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloValidator {
    private static final Logger logger = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard(final TrelloCard trelloCard) {
        if (trelloCard.getName().contains("test")) {
            logger.info("Someone is testing my application.");
        } else {
            logger.info("Seems that my application is used in proper way");
        }
    }

    public List<TrelloBoard> validateTrelloBoard(final List<TrelloBoard> trelloBoards) {
        logger.info("Starting filtering boards ...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        logger.info("Boards have been filtered. Current list size: " + filteredBoards.size());

        return filteredBoards;
    }
}
