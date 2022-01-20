package ma.enset.blockchain.web;

import ma.enset.blockchain.dtos.BlockchainDto;
import ma.enset.blockchain.dtos.TransactionDto;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.kafka.TransactionPublisher;
import ma.enset.blockchain.services.BlockService;
import ma.enset.blockchain.services.BlockchainService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path="/blockchain")
public class BlockchainController {


    private final BlockService blockService;
    private final BlockchainService blockchainService;
    private TransactionPublisher transactionPublisher;

    public BlockchainController(BlockService blockService, BlockchainService blockchainService, TransactionPublisher transactionPublisher, TransactionPublisher transactionPublisher1) {
        this.blockService = blockService;
        this.blockchainService = blockchainService;
        this.transactionPublisher = transactionPublisher1;
    }

    @PostMapping(path ="/create")
    public Blockchain createBlockchain(@RequestBody BlockchainDto blockchainDto){
        return blockchainService.createBlockchain(blockchainDto.getName(), blockchainDto.getDifficulty(), blockchainDto.getMiningReward());
    }

    @PostMapping(path="/submitTransaction")
    public String submitTransaction(@RequestBody TransactionDto dto){
        System.out.println("here");
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                dto.getAddressSource(),
                dto.getAddressDestination(),
                dto.getAmount()
        );

        transactionPublisher.publish(transaction);
        return "transaction published seccussefuly";


    }

}
