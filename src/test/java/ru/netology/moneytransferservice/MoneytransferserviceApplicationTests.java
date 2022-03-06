package ru.netology.moneytransferservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.moneytransferservice.repository.CardsRepository;
import ru.netology.moneytransferservice.trasferdata.Amount;
import ru.netology.moneytransferservice.trasferdata.ConfirmData;
import ru.netology.moneytransferservice.trasferdata.TransferData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MoneytransferserviceApplicationTests {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ConfirmData confirmData;

    @Autowired
    CardsRepository cardsRepository;

    @Test
    public void getTransferOperationId() throws Exception {
        TransferData transferData = TransferData.builder()
                .amount(new Amount("RUR", 111))
                .cardFromCVV("123")
                .cardFromNumber("1111111111111111")
                .cardFromValidTill("11/22")
                .cardToNumber("2222222222222222")
                .build();
        String json = mapper.writeValueAsString(transferData);
        String actual = mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expected = "1";
        Assertions.assertEquals(expected, actual);

    }
    @Test
    public void confirmOperation() {
        TransferData transferData = TransferData.builder()
                .amount(new Amount("RUR", 111))
                .cardFromCVV("123")
                .cardFromNumber("1111111111111111")
                .cardFromValidTill("11/22")
                .cardToNumber("2222222222222222")
                .build();
        ConfirmData confirmData = Mockito.mock(ConfirmData.class);
        Mockito.when(confirmData.getCode()).thenReturn("0000");
        cardsRepository.setTransferDataAllowed(transferData);
        cardsRepository.setCardToTransferIndex(1);
        cardsRepository.setCardFromTransferIndex(0);
        String actual = cardsRepository.confirmOperation(confirmData);
        String expected = "0";
        Assertions.assertEquals(expected, actual);
    }
}
