package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
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

    public static boolean BLOCK_ALREADY_GOT_MINED = false;
    @KafkaListener(topics = "Blocks",groupId="block_json", containerFactory = "blockKafkaListenerFactory")
    public void consumeBlock(Block block) {
        BLOCK_ALREADY_GOT_MINED = false;
        System.out.println("Consumed JSON Message: " + block);
        block = blockService.mineBlock(block, blockchainService.getDifficulty());
        System.out.println("block got mined : nonce : " +block.getNonce() + " hash : "+ block.getHash());
        BLOCK_ALREADY_GOT_MINED = true;
    }
}
