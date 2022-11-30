package io.github.androoideka.vm_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.github.androoideka.vm_manager.model.ErrorLog;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorLog, Long>, JpaSpecificationExecutor<ErrorLog> {
}
