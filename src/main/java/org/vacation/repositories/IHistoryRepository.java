package org.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vacation.models.History;

public interface IHistoryRepository extends JpaRepository<History, Long> {
}
