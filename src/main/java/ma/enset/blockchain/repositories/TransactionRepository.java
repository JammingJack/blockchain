package ma.enset.blockchain.repositories;

import ma.enset.blockchain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
