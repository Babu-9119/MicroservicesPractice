package com.letsbuild.cards.service;

import com.letsbuild.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param mobileNumber - mobile number of a customer
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile number
     * @return card details based on given mobile number
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     *
     * @param cardsDto - cardsDto object
     * @return boolean indication update of card details successful or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber - input mobile number
     * @return boolean indicating weather deletion of card details successful or not
     */
    boolean deleteCard(String mobileNumber);

}
