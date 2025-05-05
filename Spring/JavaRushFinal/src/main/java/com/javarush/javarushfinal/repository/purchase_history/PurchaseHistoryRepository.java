package com.javarush.javarushfinal.repository.purchase_history;

import com.javarush.javarushfinal.entity.PurchaseHistory;
import com.javarush.javarushfinal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findByUser(User user);

}
