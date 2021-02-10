package org.vacation.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vacation.beans.HistoryDto;
import org.vacation.models.History;
import org.vacation.repositories.IVacationRepository;

@Component
public class HistoryTransformer extends Transformer<HistoryDto, History> {

    @Autowired
    IVacationRepository vacationRepository;

    @Override
    public HistoryDto toDto(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setHistoryId(history.getHistoryId());
        historyDto.setMessage(history.getMessage());
        historyDto.setActionBy(history.getActionBy());
        historyDto.setTime(history.getTime());
        historyDto.setVacationId(history.getVacation().getId());
        return historyDto;
    }

    @Override
    public History toEntity(HistoryDto historyDto) {
        History history = new History();
        history.setMessage(historyDto.getMessage());
        history.setActionBy(historyDto.getActionBy());
        history.setTime(historyDto.getTime());
        history.setVacation(vacationRepository.getOne(historyDto.getVacationId()));
        return history;
    }
}
