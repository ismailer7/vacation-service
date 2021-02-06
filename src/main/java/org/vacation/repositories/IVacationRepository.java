package org.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vacation.models.Vacation;

import java.util.List;

@Repository
public interface IVacationRepository extends JpaRepository<Vacation, Long> {

}
