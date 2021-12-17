package ma.enset.blockchain.services;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;

import java.util.List;

public interface BlockchainService {
    public Blockchain createBlockchain();
    public Block mineBlock(List<Transaction> transactionList, String previousHash);
    public Block getLastBlock();
    public boolean isBlockchainValid();
    public double getAddressBalance(String address);

}
