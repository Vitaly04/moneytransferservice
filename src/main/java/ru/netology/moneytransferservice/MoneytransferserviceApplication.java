package ru.netology.moneytransferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;
import ru.netology.moneytransferservice.card.Card;
import ru.netology.moneytransferservice.controller.MoneyTransferServiceController;
import ru.netology.moneytransferservice.repository.CardsRepository;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@SpringBootApplication
public class MoneytransferserviceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MoneytransferserviceApplication.class, args);
        MoneyTransferService service = context.getBean(MoneyTransferService.class);
        CardsRepository cardsRepository = context.getBean(CardsRepository.class);
        Card card = context.getBean(Card.class);
    }
}
