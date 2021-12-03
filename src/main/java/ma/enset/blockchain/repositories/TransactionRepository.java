package ma.enset.blockchain.repositories;

import ma.enset.blockchain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
