package org.gabriela.simplebank.transactions;

import org.gabriela.simplebank.accounts.Account;
import org.gabriela.simplebank.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transactions")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();
        long amount = transaction.getAmount();
        if (fromAccount == null || toAccount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Account newFromAccount = accountRepository.getOne(fromAccount.getId());
        Account newToAccount = accountRepository.getOne(toAccount.getId());
        if (newFromAccount == null || newToAccount == null) {
            return ResponseEntity.notFound().build();
        }

        if (newFromAccount.getBalance() - amount >= 0) {
            newFromAccount.setBalance(newFromAccount.getBalance() - amount);
            newToAccount.setBalance(newToAccount.getBalance() + amount);
        } else {
            return ResponseEntity.badRequest().build();
        }
        accountRepository.save(newFromAccount);
        accountRepository.save(newToAccount);
        Transaction newTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(newTransaction);
    }
}
