package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.dtos.FinishedMiningMessageDto;
import ma.enset.blockchain.entities.Block;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor

@Component
public class MiningMessagesPublisher {
    private static final String MINING_MESSAGES_TOPIC = "Mining_Messages";
    private KafkaTemplate<String, FinishedMiningMessageDto> kafkaMiningMessagesTemplate;


    public boolean publish(FinishedMiningMessageDto dto){
        try{
            kafkaMiningMessagesTemplate.send(MINING_MESSAGES_TOPIC, dto);
            System.out.println("published block");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
