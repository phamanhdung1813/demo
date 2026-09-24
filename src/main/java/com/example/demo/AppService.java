package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void serviceAPlus() {
        Optional<AppEntity> currentEntity = repository.findById(1L);
        if (currentEntity.isPresent()) {
            AppEntity a = currentEntity.get();
            a.setAmount(a.getAmount() + 100L);
            repository.save(a);
        }
    }
}