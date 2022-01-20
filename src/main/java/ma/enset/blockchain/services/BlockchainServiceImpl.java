package ma.enset.blockchain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.kafka.BlockPublisher;
import ma.enset.blockchain.kafka.TransactionPublisher;
import ma.enset.blockchain.repositories.BlockchainRepository;
import ma.enset.blockchain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class BlockchainServiceImpl implements BlockchainService {
    private final static int BLOCK_SIZE = 3;
    public static boolean BLOCK_ALREADY_GOT_MINED = false;
    @Getter @Setter
    private String instanceOwnerAdress ;
    private BlockService blockService;
    @Getter private Block currentBlockToBeMined;
    private Blockchain blockchain;
    private BlockchainRepository blockchainRepository;
    private ArrayList<Transaction> pendingTransactions;
    private TransactionRepository transactionRepository;
    private BlockPublisher blockPublisher;
    private TransactionPublisher transactionPublisher;
    public BlockchainServiceImpl(BlockService blockService, BlockchainRepository blockchainRepository, TransactionRepository transactionRepository, BlockPublisher blockPublisher, TransactionPublisher transactionPublisher) {
        this.blockService = blockService;
        this.blockchainRepository = blockchainRepository;
        this.transactionRepository = transactionRepository;
        this.blockPublisher = blockPublisher;
        this.transactionPublisher = transactionPublisher;
        this.pendingTransactions = new ArrayList<>();
    }

    public Blockchain getBlockchain(){
        return this.blockchain;
    }


    @Override
    public Blockchain createBlockchain(String name, int difficulty, int miningReward) {
        this.blockchain = new Blockchain();
        Block genesisBlock = blockService.createBlock(Collections.emptyList(), "0");
        blockchain.setId(UUID.randomUUID().toString());
        blockchain.setName(name);
        blockchain.setDifficulty(difficulty);
        blockchain.setMiningReward(miningReward);
        blockchain.setBlocks(new LinkedList<Block>());
        System.out.println("inside create blockchaine " + genesisBlock.getId());
        blockchain.getBlocks().add(genesisBlock);
        return blockchainRepository.save(blockchain);
    }

    @Override
    public boolean addTransactionToPendingTransactions(Transaction transaction) {
        boolean added = pendingTransactions.add(transaction);

        ObjectMapper objectMapper = new ObjectMapper();
        transactionRepository.save(transaction);
        if(pendingTransactions.size()==BLOCK_SIZE){
            BLOCK_ALREADY_GOT_MINED = false;
            //we create a block and start to mine it
            Block block = blockService.createBlock(pendingTransactions.subList(0,BLOCK_SIZE),getLastBlock().getHash());
            try {
                currentBlockToBeMined = objectMapper
                        .readValue(objectMapper.writeValueAsString(block), Block.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.println("addTransactionToPendingTransactions " + currentBlockToBeMined.toString()    );
            //publish the block to the broker to be mined by all miners
            blockPublisher.publish(block);
            pendingTransactions.clear();
        }
        return added;
    }

    @Override
    public boolean mineBlock(Block block, String minerAddress) {
        //block = blockService.mineBlock(block, blockchain.getDifficulty());
        try{
            Transaction rewardTransaction = new Transaction(
                    UUID.randomUUID().toString(),
                    new Date(System.currentTimeMillis()),
                    minerAddress,
                    minerAddress,
                    blockchain.getMiningReward()
            );
            addTransactionToPendingTransactions(rewardTransaction);
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
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
                if(transaction.getAddressSource().equals(address) && !transaction.getAddressDestination().equals(address)){
                    balance-= transaction.getAmount();
                }
            }
        }
        return balance;
    }
}
