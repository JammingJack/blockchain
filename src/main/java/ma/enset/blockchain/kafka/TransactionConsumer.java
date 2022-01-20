package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.entities.Transaction;
import ma.enset.blockchain.services.BlockchainService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class TransactionConsumer {
    private BlockchainService blockchainService;

    @KafkaListener(topics = "Transactions",groupId="transaction_json", containerFactory = "transactionKafkaListenerFactory")
    public void consumeTransaction(Transaction transaction) {
        System.out.println("Consumed JSON Message: " + transaction);
        blockchainService.addTransactionToPendingTransactions(transaction);
    }
}
