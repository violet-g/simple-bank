package org.gabriela.simplebank.accounts;

import org.gabriela.simplebank.transactions.Transaction;
import org.gabriela.simplebank.transactions.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long id) {
        Account account = accountRepository.getOne(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<Page<Transaction>> getTransactionsByAccountId(@PathVariable("id") long id, Pageable pageable) {
        Account account = accountRepository.getOne(id);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping(value="/accounts", consumes={"application/json"})
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountRepository.save(account);
        return ResponseEntity.ok(newAccount);
    }
}
