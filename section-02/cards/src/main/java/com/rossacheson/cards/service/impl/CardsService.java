package com.rossacheson.cards.service.impl;

import com.rossacheson.cards.repository.CardsRepository;
import com.rossacheson.cards.constants.CardsConstants;
import com.rossacheson.cards.dto.CardDto;
import com.rossacheson.cards.entity.Card;
import com.rossacheson.cards.exception.CardAlreadyExistsException;
import com.rossacheson.cards.exception.ResourceNotFoundException;
import com.rossacheson.cards.mapper.CardsMapper;
import com.rossacheson.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsService implements ICardsService {
    private CardsRepository cardsRepository;

    /**
     * Creates a new loan for the given mobile phone number.
     *
     * @param mobileNumber the mobile number to be associated with the loan
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * Retrieves the card details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the card
     * @return the card details
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardDto(card, new CardDto());
    }

    /**
     * Updates the card details.
     *
     * @param cardDto the data transfer object containing card details
     * @return true if card was updated successfully, false otherwise
     */
    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardsRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        CardsMapper.mapToCard(cardDto, card);
        cardsRepository.save(card);
        return true;
    }

    /**
     * Deletes the card associated with the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the card
     * @return true if card was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getCardId());
        return true;
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
