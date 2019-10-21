package org.gabriela.simplebank.transactions;

import com.fasterxml.jackson.annotation.*;
import org.gabriela.simplebank.accounts.Account;

import javax.persistence.*;

@Entity
@Table(name = "transaction", indexes = {
        @Index(name = "idx_from_account",  columnList = "from_account"),
        @Index(name = "idx_to_account", columnList = "to_account")})
@JsonIgnoreProperties( { "hibernateLazyInitializer" } )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name="from_account")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name="to_account")
    private Account toAccount;

    @Column
    private long amount;

    public long getId() {
        return id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public long getAmount() {
        return amount;
    }
}
