package ma.enset.blockchain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FinishedMiningMessageDto {
      private boolean BLOCK_ALREADY_GOT_MINED;
      private String minerAdress;
      private int nonce;
}
