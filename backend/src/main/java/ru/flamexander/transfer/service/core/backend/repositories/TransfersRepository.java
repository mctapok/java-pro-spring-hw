package ru.flamexander.transfer.service.core.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;


public interface TransfersRepository extends JpaRepository<Transfer, Long> {
    Page<Transfer> findAllByClientIdOrderByTransferDateDesc(Long clientId, Pageable pageable);
}
