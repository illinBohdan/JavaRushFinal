package com.javarush.javarushfinal.service.part;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.repository.part.PartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartServiceImplementTest {

    @Mock
    private PartRepository partRepository;

    @InjectMocks
    private PartServiceImplement partService;

    @Test
    void testGetAllParts() {

        Part part1 = new Part();
        part1.setId(1L);
        part1.setName("Part A");

        Part part2 = new Part();
        part2.setId(2L);
        part2.setName("Part B");

        List<Part> mockParts = Arrays.asList(part1, part2);

        when(partRepository.findAll()).thenReturn(mockParts);

        List<Part> parts = partService.getAllParts();

        assertEquals(2, parts.size());
        assertEquals("Part A", parts.get(0).getName());
        assertEquals("Part B", parts.get(1).getName());
    }


    @Test
    void testSavePart() {

        Part part = new Part();
        part.setId(1L);
        part.setName("Part A");

        partService.savePart(part);

        verify(partRepository).save(part);

    }

    @Test
    void testGetPartById() {
        Part part = new Part();
        part.setId(1L);
        part.setName("Part A");

        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        Part foundPart = partService.getPartById(1L);

        assertNotNull(foundPart);
        assertEquals(1L, foundPart.getId());
        assertEquals("Part A", foundPart.getName());

    }

    @Test
    void testDeletePartById() {
        Long partId = 1L;

        partService.deletePartById(partId);

        verify(partRepository).deleteById(partId);

    }

    @Test
    void testGetAllPartsByAuto() {

        Part part1 = new Part();
        part1.setId(1L);
        part1.setName("Part A");
        part1.setAuto("BMW");

        Part part2 = new Part();
        part2.setId(2L);
        part2.setName("Part B");
        part2.setAuto("BMW");

        List<Part> mockParts = Arrays.asList(part1, part2);

        when(partRepository.findByAuto("BMW")).thenReturn(mockParts);

        List<Part> parts = partService.getAllPartsByAuto("BMW");

        assertEquals(2, parts.size());
        assertEquals("BMW", parts.get(0).getAuto());
        assertEquals("BMW", parts.get(1).getAuto());

    }

    @Test
    void testIncrementViewCount() {

        Part part = new Part();
        part.setId(1L);
        part.setName("Part A");
        part.setShowCount(5L); // Початкове значення лічильника


        when(partRepository.findById(1L)).thenReturn(Optional.of(part));
        partService.incrementViewCount(1L);
        assertEquals(6, part.getShowCount());

        verify(partRepository).save(part);

    }

    @Test
    void testIncrementViewCount_PartNotFound() {

        // Налаштовуємо мок-репозиторій для випадку, коли запчастину не знайдено
        when(partRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            partService.incrementViewCount(2L);
        });


        assertEquals("Запчастина не знайдена: ID = 2", exception.getMessage());
    }


    @Test
    void testGetViewCount() {
        Part part = new Part();
        part.setId(1L);
        part.setName("Part A");
        part.setShowCount(10L);

        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        Long viewCount = partService.getViewCount(1L);

        assertEquals(10, viewCount);

    }

    @Test
    void testGetViewCount_PartNotFound() {

        when(partRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            partService.getViewCount(2L);
        });

        assertEquals("Запчастина не знайдена: ID = 2", exception.getMessage());
    }

}