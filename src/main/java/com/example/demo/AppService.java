package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppService {
    private static final Logger log =
            LoggerFactory.getLogger(AppService.class);
    @Autowired
    private io.micrometer.tracing.Tracer tracer;

    @Lazy
    @Autowired
    private AppService self;

    private final AppRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
        var span = tracer.currentSpan();
        log.info("methodA currentSpanId={}", span);
        log.info("methodA traceId={}", MDC.get("traceId"));
        log.info("Thread Transaction on  methodA={}", Thread.currentThread().threadId());
        Optional<AppEntity> currentEntity = repository.findById(1L);
        if (currentEntity.isPresent()) {
            AppEntity a = currentEntity.get();
            a.setAmount(a.getAmount() + 100L);
            repository.save(a);
            try {
                this.methodB();
            } catch (Exception e) {
                log.error("Error in methodA while calling methodB", e);
                log.info("rollbackOnly={}",
                        TransactionAspectSupport
                                .currentTransactionStatus()
                                .isRollbackOnly());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void methodB() {
        var span = tracer.currentSpan();
        log.info("methodB currentSpanId={}", span);
        log.info("methodB traceId={}", MDC.get("traceId"));
        log.info("Thread Transaction on  methodB={}", Thread.currentThread().threadId());
        Optional<AppEntity> currentEntity = repository.findById(1L);
        if (currentEntity.isPresent()) {
            AppEntity a = currentEntity.get();
            a.setAmount(a.getAmount() - 200L);
            repository.save(a);
            throw new RuntimeException("Intentional Exception in methodB");
        }
    }

    public Iterable<AppEntity> getAll() {
        return repository.findAll();
    }
}