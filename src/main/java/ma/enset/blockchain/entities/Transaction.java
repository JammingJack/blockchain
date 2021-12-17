package ma.enset.blockchain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Date date;
    private String addressSource;
    private String addressDestination;
    private double amount;
}
