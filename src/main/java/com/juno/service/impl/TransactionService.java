package com.juno.service.impl;

import com.juno.DTO.TransactionDTO;
import com.juno.entity.Transaction;
import com.juno.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public Transaction getTransactionById(Long id) {
        return transactionRepo.findById(id).get();
    }

    public Long createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setDistrict(transactionDTO.getDistrict());
        transaction.setIdUser(transactionDTO.getIdUser());
        transaction.setFullName(transactionDTO.getFullName());
        transaction.setEmail(transactionDTO.getEmail());
        transaction.setPhone(transactionDTO.getPhone());
        transaction.setCity(transactionDTO.getCity());
        transaction.setDistrict(transactionDTO.getDistrict());
        transaction.setWard(transactionDTO.getWard());
        transaction.setAddressDetail(transactionDTO.getAddressDetail());
        transaction.setListProductItem(transactionDTO.getListProductItem());
        transaction.setMoney(transactionDTO.getMoney());
        transaction.setNote(transactionDTO.getNote());
        transaction.setMoney_reduced(transactionDTO.getMoneyReduced());
        transactionRepo.save(transaction);
        return transaction.getId();
    }
    
    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
    }

}
