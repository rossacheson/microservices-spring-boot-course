package com.rossacheson.cards.service;

import com.rossacheson.cards.dto.CardDto;

public interface ICardsService {
    /**
     * Creates a new card for the given mobile phone number.
     *
     * @param mobileNumber the mobile number to be associated with the card
     */
    void createCard(String mobileNumber);
    /**
     * Retrieves the card details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the card
     * @return the card details
     */
    CardDto fetchCard(String mobileNumber);

    /**
     * Updates the card details.
     *
     * @param cardDto the data transfer object containing card details
     * @return true if card was updated successfully, false otherwise
     */
    boolean updateCard(CardDto cardDto);

    /**
     * Deletes the card associated with the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the card
     * @return true if card was deleted successfully, false otherwise
     */
    boolean deleteCard(String mobileNumber);
}
