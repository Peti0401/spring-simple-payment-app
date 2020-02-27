package hu.gyarmatip.simplepaymentapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false, columnDefinition = "double default 200.0")
    private Double balance = 200.0;

    @JsonManagedReference
    @OneToMany(mappedBy = "senderAccount", targetEntity = Transaction.class)
    private Set<Transaction> sentTransactions = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "recipientAccount", targetEntity = Transaction.class)
    private Set<Transaction> receivedTransactions = new HashSet<>();

}
