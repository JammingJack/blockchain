package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.dtos.FinishedMiningMessageDto;
import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.services.BlockService;
import ma.enset.blockchain.services.BlockchainService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class BlockConsumer {
    private BlockchainService blockchainService;
    private BlockService blockService;
    private BlockPublisher blockPublisher;
    private MiningMessagesPublisher miningMessagesPublisher;

    @KafkaListener(topics = "Blocks",groupId="block_json", containerFactory = "blockKafkaListenerFactory")
    public void consumeBlock(Block block) {
        System.out.println("Consumed JSON Message: " + block);
        block = blockService.mineBlock(block, blockchainService.getBlockchain().getDifficulty());
        miningMessagesPublisher.publish(new FinishedMiningMessageDto(
                true,
                blockchainService.getInstanceOwnerAdress(),
                block.getNonce()
        ));
        System.out.println("block got mined : nonce : " +block.getNonce() + " hash : "+ block.getHash());
    }
}
