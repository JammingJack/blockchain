package ma.enset.blockchain.services;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.kafka.BlockConsumer;
import ma.enset.blockchain.repositories.BlockRepository;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class BlockServiceImpl implements BlockService {
    private BlockRepository blockRepository;
    private BlockConsumer blockConsumer;
    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }
    @Override
    public Block createBlock(List<Transaction> list, String previousHash) {
        Block block = new Block();
        block.setId(UUID.randomUUID().toString());
        block.setCreatedAt(new Date(System.currentTimeMillis()));
        block.setPreviousHash(previousHash);
        block.setNonce(0);
        block.setTransactions(list);
        block.setHash(getBlockHash(block));
        System.out.println("inside create block" + block.getId());
        blockRepository.save(block);
        return block;
    }

    @Override
    public String getBlockHash(Block block) {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String strToHash = block.toString();
            byte[] encodinghash = messageDigest.digest(
                    strToHash.getBytes(StandardCharsets.UTF_8)
            );
            return bytesToHex(encodinghash);
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Block mineBlock(Block block, int difficulty) {
        String prefix = new String(new char[difficulty]).replace('\0','0');
        String hash = getBlockHash(block);
        while(!hash.startsWith(prefix, 0) && !BlockchainServiceImpl.BLOCK_ALREADY_GOT_MINED){
            //System.out.println(hash);
            block.setNonce(block.getNonce()+1);
            hash = getBlockHash(block);
        }
        block.setHash(hash);
        return block;
    }

    @Override
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
