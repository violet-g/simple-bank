package org.gabriela.simplebank.accounts;

import com.fasterxml.jackson.annotation.*;
import org.gabriela.simplebank.transactions.Transaction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account")
@JsonIgnoreProperties( { "hibernateLazyInitializer" } )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private long balance;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private Set<Transaction> fromTransactions;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private Set<Transaction> toTransactions;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Set<Transaction> getFromTransactions() {
        return fromTransactions;
    }

    public Set<Transaction> getToTransactions() {
        return toTransactions;
    }
}
