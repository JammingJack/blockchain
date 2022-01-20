package ma.enset.blockchain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String addressSource;
    private String addressDestination;
    private double amount;
}
