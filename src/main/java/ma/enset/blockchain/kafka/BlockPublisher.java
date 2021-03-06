package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.dtos.FinishedMiningMessageDto;
import ma.enset.blockchain.entities.Block;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@AllArgsConstructor

@Component
public class BlockPublisher {
    private static final String BLOCKS_TOPIC = "Blocks";
    private KafkaTemplate<String, Block> kafkaBlocksTemplate;

    public boolean publish(Block block){
        try{
            kafkaBlocksTemplate.send(BLOCKS_TOPIC, block);
            System.out.println("published block");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
