package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@AllArgsConstructor

@Component
public class BlockPublisher {
    private static final String BLOCKS_TOPIC = "Blocks";
    private KafkaTemplate<String, Block> kafkaTemplate;

    public boolean publish(Block block){
        try{
            kafkaTemplate.send(BLOCKS_TOPIC, block);
            System.out.println("published block");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
}
