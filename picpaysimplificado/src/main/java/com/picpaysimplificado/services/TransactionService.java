package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDto;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;


@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User sender = this.userService.findUserById(transactionDto.senderId());
        User receiver = this.userService.findUserById(transactionDto.receiverId());

        userService.validateTransaction(sender, transactionDto.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transactionDto.value());

        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(newTransaction.getAmount());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");


        return newTransaction;
    }

}
