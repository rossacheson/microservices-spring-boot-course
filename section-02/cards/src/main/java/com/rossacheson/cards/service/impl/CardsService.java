package com.rossacheson.cards.service.impl;

import com.rossacheson.cards.repository.CardsRepository;
import com.rossacheson.cards.constants.CardsConstants;
import com.rossacheson.cards.dto.CardDto;
import com.rossacheson.cards.entity.Card;
import com.rossacheson.cards.exception.CardAlreadyExistsException;
import com.rossacheson.cards.exception.ResourceNotFoundException;
import com.rossacheson.cards.mapper.CardsMapper;
import com.rossacheson.cards.repository.CardsRepository;
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
     * Retrieves the loan details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the loan
     * @return the loan details
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card loan = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardDto(loan, new CardDto());
    }

    /**
     * Updates the loan details.
     *
     * @param loanDto the data transfer object containing loan details
     * @return true if loan was updated successfully, false otherwise
     */
    @Override
    public boolean updateCard(CardDto loanDto) {
        Card loan = cardsRepository.findByCardNumber(loanDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", loanDto.getCardNumber()));
        CardsMapper.mapToCard(loanDto, loan);
        cardsRepository.save(loan);
        return  true;
    }

    /**
     * Deletes the loan associated with the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the loan
     * @return true if loan was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card loan = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(loan.getCardId());
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
