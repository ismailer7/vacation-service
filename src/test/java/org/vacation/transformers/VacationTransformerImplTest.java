package org.vacation.transformers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.vacation.beans.UserDto;
import org.vacation.beans.VacationDto;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.services.impl.UserServiceImpl;
import org.vacation.utils.StatusEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VacationTransformerImplTest {

    @InjectMocks
    private VacationTransformerImpl vacationTransformer;

    private Vacation vacation = null;
    private VacationDto vacationDto = null;
    private User user = null;
    private List<Vacation> vacationList = null;
    private List<VacationDto> vacationDtoList = null;


    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Mock
    private UserServiceImpl userService;

    @Spy
    private UserTranformerImpl userTransformer;

    @Before
    public void setUp() throws ParseException {
        user = new User(1l, "ir91995");
        StatusEnum status = StatusEnum.getByOrdinal(1);

        vacation = new Vacation(1l, "vacation title test",
                    format.parse("10/02/2021"),
                    format.parse("22/02/2021"),
                    status.ordinal(), user, "ir45698");

        vacationDto = new VacationDto(12547l, "vacation dto title", "05/05/2020", "06/05/2021", 1, 10l, "ir45698");

        vacationList = Arrays.asList(
                vacation,
                new Vacation(107l, "vacation title test 107",
                        format.parse("11/04/2021"),
                        format.parse("28/04/2021"),
                        status.ordinal(), user, "ir45698")
        );

        vacationDtoList = Arrays.asList(
                vacationDto,
                new VacationDto(1547l, "vacation dto title test 1547", "01/08/2021", "31/08/2021", 1, 10l, "ir45698")
        );

        when(userService.getById(anyLong())).thenReturn(new UserDto(10l, "testUser"));
    }

    @Test
    public void testToDto() {
        VacationDto vacationDto = vacationTransformer.toDto(vacation);
        assertNotNull(vacationDto);
        assertEquals(vacation.getId(), (long) vacationDto.getVacationId());
        assertEquals(format.format(vacation.getStartDate()), vacationDto.getStartDate());
        assertEquals(format.format(vacation.getEndDate()), vacationDto.getEndDate());
        assertEquals(vacation.getTitle(), vacationDto.getVacationTitle());
        assertEquals(vacation.getStatus(), vacationDto.getStatus());
        assertEquals(vacation.getUser().getId(), vacationDto.getUserId());
        assertEquals(vacation.getAssignment(), vacationDto.getAssignment());
    }

    @Test
    public void testToEntity() {
        Vacation vacation = vacationTransformer.toEntity(vacationDto);
        assertNotNull(vacation);
        assertEquals(vacationDto.getVacationTitle(), vacation.getTitle());
        assertEquals(vacationDto.getStartDate(), format.format(vacation.getStartDate()));
        assertEquals(vacationDto.getEndDate(), format.format(vacation.getEndDate()));
        assertEquals(vacationDto.getStatus(), vacation.getStatus());
        assertEquals(vacationDto.getUserId(), vacation.getUser().getId());
        assertEquals(vacationDto.getAssignment(), vacation.getAssignment());
    }

    @Test
    public void toDtoList() {
        List<VacationDto> vacationDtoList = vacationTransformer.toDtoList(vacationList);
        assertNotNull(vacationDtoList);
        int i = 0;
        for (VacationDto vacationDto : vacationDtoList) {
            assertEquals( (long) vacationDto.getVacationId(), vacationList.get(i).getId());
            assertEquals(vacationDto.getVacationTitle(), vacationList.get(i).getTitle());
            assertEquals(vacationDto.getStartDate(), format.format(vacationList.get(i).getStartDate()));
            assertEquals(vacationDto.getEndDate(), format.format(vacationList.get(i).getEndDate()));
            assertEquals(vacationDto.getStatus(), vacationList.get(i).getStatus());
            assertEquals(vacationDto.getUserId(), vacationList.get(i).getUser().getId());
            assertEquals(vacationDto.getAssignment(), vacationList.get(i).getAssignment());
            i ++;
        }
    }

    @Test
    public void toEntityList() {
        List<Vacation> vacationList = vacationTransformer.toEntityList(vacationDtoList);
        assertNotNull(vacationList);
        int i = 0;
        for (Vacation vacation : vacationList) {
            assertEquals(vacationDtoList.get(i).getVacationTitle(), vacation.getTitle());
            assertEquals(vacationDtoList.get(i).getStartDate(), format.format(vacation.getStartDate()));
            assertEquals(vacationDtoList.get(i).getEndDate(), format.format(vacation.getEndDate()));
            assertEquals(vacationDtoList.get(i).getStatus(), vacation.getStatus());
            assertEquals(vacationDtoList.get(i).getUserId(), vacation.getUser().getId());
            assertEquals(vacationDtoList.get(i).getAssignment(), vacation.getAssignment());
            i ++;
        }
    }
}
