package ma.enset.blockchain.repositories;

import ma.enset.blockchain.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BlockRepository extends JpaRepository<Block, String> {
}
