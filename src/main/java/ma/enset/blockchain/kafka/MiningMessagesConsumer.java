package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.dtos.FinishedMiningMessageDto;
import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.services.BlockService;
import ma.enset.blockchain.services.BlockchainService;
import ma.enset.blockchain.services.BlockchainServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service @AllArgsConstructor
public class MiningMessagesConsumer {
    private BlockchainService blockchainService;
    private BlockService blockService;


    @KafkaListener(topics = "Mining_Messages",groupId="miningMessages_json", containerFactory = "miningMessagesKafkaListenerFactory")
    public void consumeBlock(FinishedMiningMessageDto dto) {
        String prefix = new String(new char[blockchainService.getBlockchain().getDifficulty()]).replace('\0','0');
        Block currentBlock = blockchainService.getCurrentBlockToBeMined();
        Block blockToVerify = new Block(
                currentBlock.getId(),
                currentBlock.getCreatedAt(),
                currentBlock.getHash(),
                currentBlock.getPreviousHash(),
                currentBlock.getTransactions().stream().filter(i->true).collect(Collectors.toList()),
                dto.getNonce()
        );
        if(blockService.getBlockHash(blockToVerify).startsWith(prefix, 0)){
            BlockchainServiceImpl.BLOCK_ALREADY_GOT_MINED = true;
            blockService.saveBlock(blockToVerify);
            blockchainService.getBlockchain().getBlocks().add(blockToVerify);
            blockchainService.mineBlock(blockToVerify, dto.getMinerAdress());
        }
    }
}
