package ma.enset.blockchain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Blockchain {
    @Id
    private String id;
    private String name;
    private int difficulty;
    private int miningReward;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Block> blocks;

}
