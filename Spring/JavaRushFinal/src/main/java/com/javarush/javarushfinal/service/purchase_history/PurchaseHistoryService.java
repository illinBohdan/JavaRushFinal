package com.javarush.javarushfinal.service.purchase_history;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.entity.PurchaseHistory;
import com.javarush.javarushfinal.entity.User;

import java.util.List;

public interface PurchaseHistoryService {
     void savePurchase(User user, Part part, int quantity);
     List<PurchaseHistory> findByUser(User user);

}
