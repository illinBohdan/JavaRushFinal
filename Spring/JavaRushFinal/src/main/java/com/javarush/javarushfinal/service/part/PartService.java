package com.javarush.javarushfinal.service.part;

import com.javarush.javarushfinal.entity.Part;

import java.util.List;

public interface PartService {

    List<Part> getAllParts();
    void savePart(Part part);
    Part getPartById(Long id);
    void deletePartById(Long id);
    List<String> getUniqueValues(String fieldName);
    List<Part> getAllPartsByAuto(String auto);
    void incrementViewCount(Long id);
    Long getViewCount(Long id);
    void reducingQuantityPart(Long partId, Integer quantity);

}
