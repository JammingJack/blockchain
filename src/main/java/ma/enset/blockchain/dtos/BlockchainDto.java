package ma.enset.blockchain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BlockchainDto {
    private String name;
    private int difficulty;
    private int miningReward;
}
