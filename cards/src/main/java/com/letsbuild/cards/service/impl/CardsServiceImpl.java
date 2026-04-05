package com.letsbuild.cards.service.impl;

import com.letsbuild.cards.constants.CardsConstants;
import com.letsbuild.cards.dto.CardsDto;
import com.letsbuild.cards.entity.Cards;
import com.letsbuild.cards.exception.CardAlreadyExistsException;
import com.letsbuild.cards.exception.ResourceNotFoundException;
import com.letsbuild.cards.mapper.CardsMapper;
import com.letsbuild.cards.repository.CardsRepository;
import com.letsbuild.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    /**
     *
     * @param mobileNumber - mobile number of a customer
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);

        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already exist with the given mobile number :"+mobileNumber);
        }

        System.out.println("New card created at "+createNewCard(mobileNumber).getCreatedAt());

        cardsRepository.save(createNewCard(mobileNumber));


    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy("ADMIN");
        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input mobile number
     * @return card details based on given mobile number
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card","Mobile Number",mobileNumber));

        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    /**
     *
     * @param cardsDto - cardsDto object
     * @return boolean indication update of card details successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(() -> new ResourceNotFoundException("Card","Mobile Number",cardsDto.getMobileNumber()));

        CardsMapper.mapToCards(cardsDto,cards);
        cardsRepository.save(cards);

        return true;
    }

    /**
     *
     * @param mobileNumber - input mobile number
     * @return boolean indicating weather deletion of card details successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card","Mobile Number",mobileNumber));

        cardsRepository.deleteById(cards.getCardId());

        return true;
    }
}
