package com.javarush.javarushfinal.service.purchase_history;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.entity.PurchaseHistory;
import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.repository.purchase_history.PurchaseHistoryRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseHistoryServiceImplement implements PurchaseHistoryService{

    private final PurchaseHistoryRepository purchaseHistoryRepository;


    public PurchaseHistoryServiceImplement(PurchaseHistoryRepository purchaseHistoryRepository ) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;

    }

@Override
    public void savePurchase(User user, Part part, int quantity) {

    PurchaseHistory purchaseHistory =PurchaseHistory.builder()
            .user(user)
            .part(part)
            .quantity(quantity)
            .purchaseDate(LocalDateTime.now())
            .build();

        purchaseHistoryRepository.save(purchaseHistory);

    }

    @Override
    @Transactional
    public List<PurchaseHistory> findByUser(User user) {
        List<PurchaseHistory> purchaseHistory = purchaseHistoryRepository.findByUser(user);
        purchaseHistory.forEach(purchase -> Hibernate.initialize(purchase.getPart()));
        return purchaseHistory;
    }

}
