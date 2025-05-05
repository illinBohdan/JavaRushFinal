package com.javarush.javarushfinal.repository.part;

import com.javarush.javarushfinal.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByAuto(String auto);
}
