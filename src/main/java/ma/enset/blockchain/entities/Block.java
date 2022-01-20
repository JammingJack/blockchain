package ma.enset.blockchain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString(exclude = {"hash"})
public class Block {
    @Id
    private String id;
    private Date createdAt;

    private String hash;
    private String previousHash;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Transaction> transactions;
    private int nonce;

}
