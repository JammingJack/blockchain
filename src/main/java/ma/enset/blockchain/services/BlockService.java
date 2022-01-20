package ma.enset.blockchain.services;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BlockService {
    Block createBlock(List<Transaction> list, String previousHash);
    String getBlockHash(Block block);
    Block mineBlock(Block block, int difficulty);
    Block saveBlock(Block block);
}
