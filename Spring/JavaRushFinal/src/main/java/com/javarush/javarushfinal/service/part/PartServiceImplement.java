package com.javarush.javarushfinal.service.part;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.repository.part.PartRepository;
import com.javarush.javarushfinal.repository.part.PartRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PartServiceImplement implements PartService {

    private final PartRepository partRepository;
    private final PartRepositoryCustom repositoryCustom;

    public PartServiceImplement(PartRepository partRepository, PartRepositoryCustom repositoryCustom) {
        this.partRepository = partRepository;
        this.repositoryCustom = repositoryCustom;
    }

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @Override
    public void savePart(Part part) {
        partRepository.save(part);
    }

    @Override
    public Part getPartById(Long id) {
       return partRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Part no found with id:" + id));
    }

    @Override
    public void deletePartById(Long id) {
        partRepository.deleteById(id);
    }

    @Override
    public List<String> getUniqueValues(String fieldName) {
        return repositoryCustom.getUniqueValues(fieldName);
    }

    @Override
    public List<Part> getAllPartsByAuto(String auto) {
        return partRepository.findByAuto(auto);
    }

    @Override
    public void incrementViewCount(Long partId) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new RuntimeException("Запчастина не знайдена: ID = " + partId));
        part.setShowCount(part.getShowCount()+1);
        partRepository.save(part);
    }

    @Override
    public Long getViewCount(Long partId) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new RuntimeException("Запчастина не знайдена: ID = " + partId));
        return part.getShowCount();
    }

    @Override
    public void reducingQuantityPart(Long partId, Integer quantity) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new IllegalArgumentException("Запчастину не знайдено"));

        part.setQuantity(part.getQuantity() - quantity);
        partRepository.save(part);
    }
}
