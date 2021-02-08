package org.vacation.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.VacationDto;
import org.vacation.filters.VacationFilter;
import org.vacation.models.User;
import org.vacation.models.Vacation;
import org.vacation.repositories.IVacationRepository;
import org.vacation.services.impl.UserServiceImpl;
import org.vacation.services.impl.VacationServiceImpl;
import org.vacation.transformers.VacationTransformerImpl;
import org.vacation.utils.StatusEnum;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class VacationServiceImplTest {

    private List<VacationDto> vacationDtoList = null;
    private VacationFilter vacationFilter1 = null;
    private User user = null;
    private Object[] objs = new Object[] { new BigInteger("1256"), "01/01/2021", "15/12/2020", 1, "vacation title", 1l };
    private List<Object[]> resultListMock = new ArrayList<>();
    private Vacation vacation = null;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    @Spy
    private VacationTransformerImpl vacationTransformer;

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private Query queryMock;

    @Mock
    private UserServiceImpl userServiceMock;

    @Mock
    private IVacationRepository vacationRepositoryMock;

    @InjectMocks
    private VacationServiceImpl vacationService;

    @Before
    public void setUp() throws ParseException, java.text.ParseException {
        /**
         * TODO read file vacation and prepare list (can also store in db)
          */
        resultListMock.add(objs);
        vacationFilter1 = new VacationFilter();
        vacationFilter1.setCreatedBy("ir45698");

        user = new User();
        user.setId(1l);
        user.setUsername("testUser");

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        vacationDtoList = new ArrayList<>();
        String jsonContent = null;
        try {
            jsonContent = readJsonResourceFileToString();
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading json File.");
        }

        if(jsonContent != null && !jsonContent.isEmpty()) {
            Object object = parser.parse(jsonContent);
            jsonObject = (JSONObject) object;
            JSONArray vacactions = (JSONArray) jsonObject.get("vacations");
            Iterator<JSONObject> iterator = vacactions.iterator();
            while(iterator.hasNext()) {
                JSONObject vacation = iterator.next();
                Long vacationId = (Long) vacation.get("vacation_id");
                Long userId =  (Long) vacation.get("user_id");
                String vacationTitle = (String) vacation.get("vacation_title");
                String startDate = (String) vacation.get("start_date");
                String endDate = (String) vacation.get("end_date");
                String assignment = (String) vacation.get("assign_to");
                long status = (Long) vacation.get("status");
                int statusInt = (int) status;
                VacationDto vacationDto = new VacationDto(vacationId, vacationTitle, startDate, endDate, statusInt, userId, assignment);
                vacationDtoList.add(vacationDto);
            }
        }

        StatusEnum status = StatusEnum.getByOrdinal(1); // status created. (user_id is the same as assignment)
        vacation = new Vacation(1l, "vacation title test",
                format.parse("10/02/2021"),
                format.parse("22/02/2021"),
                1, user, "ir45698");

    }

    @Test
    public void simpleTest() {
        assertEquals(4, vacationDtoList.size());
    }

    @Test
    public void testFilter() {
        when(userServiceMock.findByUsername(anyString())).thenReturn(user);
        when(entityManagerMock.createNativeQuery(anyString())).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(resultListMock);
        List<VacationDto> vacationDtoList = vacationService.filter(vacationFilter1);
        assertNotNull(vacationDtoList);
        assertEquals(resultListMock.size(), vacationDtoList.size());
    }

    @Test
    public void testAssignVacationTo() {
        when(vacationRepositoryMock.getOne(anyLong())).thenReturn(vacation);
        when(vacationRepositoryMock.saveAndFlush(any(Vacation.class))).thenReturn(vacation);
        VacationDto result = vacationService.assignVacationTo(1l, "xd45678");
        assertNotNull(result);
        assertEquals("xd45678", result.getAssignment());
        assertEquals(2, result.getStatus());
    }

    private String readJsonResourceFileToString() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/vacation_list");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }

}
