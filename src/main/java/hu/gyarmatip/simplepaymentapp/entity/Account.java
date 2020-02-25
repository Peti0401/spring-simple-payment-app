package hu.gyarmatip.simplepaymentapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"sentTransactions", "receivedTransactions"})

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "senderAccount")
    private Set<Transaction> sentTransactions = new HashSet<>();

    @OneToMany(mappedBy = "recipientAccount")
    private Set<Transaction> receivedTransactions = new HashSet<>();

}
