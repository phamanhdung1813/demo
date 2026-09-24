package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<AppEntity, Long> {
}

