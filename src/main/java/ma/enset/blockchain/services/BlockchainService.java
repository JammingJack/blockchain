package ma.enset.blockchain.services;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;

import java.util.List;

public interface BlockchainService {
    public Blockchain createBlockchain(String name, int difficulty, int miningReward);
    public boolean addTransactionToPendingTransactions(Transaction transaction);
    public Blockchain getBlockchain();
    public void setInstanceOwnerAdress(String adress);
    public String getInstanceOwnerAdress();
    public Block getCurrentBlockToBeMined();
    public boolean mineBlock(Block block, String minerAddress);
    public Block getLastBlock();
    public boolean isBlockchainValid();
    public double getAddressBalance(String address);
}
