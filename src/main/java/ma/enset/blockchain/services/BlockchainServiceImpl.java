package ma.enset.blockchain.services;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;

import java.util.*;

public class BlockchainServiceImpl implements BlockchainService {

    private BlockService blockService;
    private Blockchain blockchain;

    public BlockchainServiceImpl(BlockService blockService) {
        this.blockService = blockService;
    }


    @Override
    public Blockchain createBlockchain() {
        Blockchain blockchain = new Blockchain();

        Block genesisBlock = blockService.createBlock(Collections.emptyList(), "0");
        blockchain.setId(UUID.randomUUID().toString());
        blockchain.setName("blockchain");
        blockchain.setDifficulty(4);
        blockchain.setMiningReward(10);
        blockchain.setBlocks(new LinkedList<Block>());
        blockchain.getBlocks().add(genesisBlock);

        this.blockchain = blockchain;
        return blockchain;
    }

    @Override
    public Block mineBlock(List<Transaction> transactionList, String previousHash) {
        Block block = blockService.createBlock(transactionList,getLastBlock().getHash());
        block = blockService.mineBlock(block, blockchain.getDifficulty());
        return block;
    }

    @Override
    public Block getLastBlock() {
        return blockchain.getBlocks().get(blockchain.getBlocks().size()-1);
    }

    @Override
    public boolean isBlockchainValid() {
        boolean isValid = true;
        List<Block> blocks = blockchain.getBlocks();
        for(int i = 0; i < blocks.size(); i++){
            if(!blocks.get(i).getHash().equals(blockService.getBlockHash(blocks.get(i)))){
                isValid = false;
                break;
            }
            if(!blocks.get(i+1).getPreviousHash().equals(blocks.get(i).getHash())){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    @Override
    public double getAddressBalance(String address) {
        double balance = 0.0;

        List<Block> blocks = blockchain.getBlocks();
        for(int i = 0; i < blocks.size(); i++){
            for(Transaction transaction: blocks.get(i).getTransactions()){
                if(transaction.getAddressDestination().equals(address)){
                    balance+= transaction.getAmount();
                }
            }
        }
        return balance;
    }
}
