package com.javarush.javarushfinal.repository.part;

import com.javarush.javarushfinal.entity.Part;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PartRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getUniqueValues(String fieldName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Part> root = query.from(Part.class);

        query.select(root.get(fieldName)).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
