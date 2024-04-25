package org.mav.banking.service;

import lombok.RequiredArgsConstructor;
import org.mav.banking.dto.TransactionResponse;
import org.mav.banking.entity.Transaction;
import org.mav.banking.entity.TransactionStatus;
import org.mav.banking.repository.AccountRepository;
import org.mav.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransactionResponse transferFunds(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        Transaction transaction = new Transaction();
        transaction.setFromAccount(accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account from not found")));
        transaction.setToAccount(accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account to not found")));
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction = transactionRepository.save(transaction);

        try {
            if (transaction.getFromAccount().getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }

            transaction.getFromAccount().setBalance(transaction.getFromAccount().getBalance().subtract(amount));
            transaction.getToAccount().setBalance(transaction.getToAccount().getBalance().add(amount));
            accountRepository.save(transaction.getFromAccount());
            accountRepository.save(transaction.getToAccount());

            transaction.setStatus(TransactionStatus.SUCCESS);
            transaction.setMessage("Funds transferred successfully");
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setMessage(e.getMessage());
        } finally {
            transaction = transactionRepository.save(transaction);
        }

        return new TransactionResponse(
                transaction.getId(),
                fromAccountId,
                toAccountId,
                amount,
                transaction.getStatus(),
                transaction.getMessage());
    }
}
