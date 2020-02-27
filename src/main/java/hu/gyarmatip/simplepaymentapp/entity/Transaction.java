package hu.gyarmatip.simplepaymentapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double sum;

    @JsonBackReference
    @ManyToOne
    private Account senderAccount;

    @JsonBackReference
    @ManyToOne
    private Account recipientAccount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime date;

}
