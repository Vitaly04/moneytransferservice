package ru.netology.moneytransferservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.moneytransferservice.card.Card;
import ru.netology.moneytransferservice.repository.CardsRepository;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@Configuration
public class JavaConfig {

    @Bean
    public CardsRepository cardsRepository() {
        return new CardsRepository();
    }

    @Bean
    public MoneyTransferService moneyTransferService(CardsRepository cardsRepository) {
        return new MoneyTransferService(cardsRepository);
    }

    @Bean
    public Card card() {
        return new Card();
    }

}
