package ma.enset.blockchain.kafka;

import lombok.AllArgsConstructor;
import ma.enset.blockchain.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TransactionPublisher {

    private static final String TRANSACTIONS_TOPIC = "Transactions";
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public boolean publish(Transaction transaction){
        try{
            kafkaTemplate.send(TRANSACTIONS_TOPIC, transaction);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
