package ma.enset.blockchain;

import ma.enset.blockchain.entities.Block;
import ma.enset.blockchain.entities.Blockchain;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.repositories.BlockRepository;
import ma.enset.blockchain.repositories.BlockchainRepository;
import ma.enset.blockchain.repositories.TransactionRepository;
import ma.enset.blockchain.services.BlockService;
import ma.enset.blockchain.services.BlockServiceImpl;
import ma.enset.blockchain.services.BlockchainService;
import ma.enset.blockchain.services.BlockchainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BlockchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);

    }
/*    @Bean
    CommandLineRunner start(TransactionRepository transactionRepository,BlockRepository blockRepository,
                            BlockchainRepository blockchainRepository, BlockService blockService) {
        return args -> {
            System.out.println("started from the bottom");
            transactionRepository.save(new Transaction("1", new Date(), "", "", 1000));
            transactionRepository.save(new Transaction("2", new Date(), "aa", "bb", 2000));
            blockRepository.save(new Block("1", new Date(), "0", "0", transactionRepository.findAll(),0));
            blockchainRepository.save(new Blockchain());
           *//* Block blk = blockRepository.getById("1");
            blk.setHash("1");*//*
            Block blk = blockService.createBlock(transactionRepository.findAll(),"0");
            System.out.println(blk.getHash());

            //blockService.createBlock(blk.getTransactions(), "0");
            Block finalBlock = blockService.mineBlock(blk,5);
            System.out.println(finalBlock.getHash());
            System.out.println(finalBlock.getNonce());
            System.out.println("Now we here");
        };
    }*/

    @Bean
    CommandLineRunner start(BlockchainService blockchainService) {
        return args -> {
            System.out.println("started from the bottom");
            blockchainService.createBlockchain("glsid_blockchain", 4, 6);
            System.out.println("Now we here");
        };
    }

}
