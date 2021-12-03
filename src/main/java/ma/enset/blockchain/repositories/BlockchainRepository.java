package ma.enset.blockchain.repositories;

import ma.enset.blockchain.entities.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BlockchainRepository extends JpaRepository<Blockchain, String> {
}
