package org.vacation.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.VacationDto;
import org.vacation.models.Vacation;
import org.vacation.repositories.IVacationRepository;
import org.vacation.transformers.VacationTransformerImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VacationServiceImplTest {

    private List<VacationDto> vacationDtoList = null;

    @Mock
    private VacationTransformerImpl vacationTransformer;

    @Before
    public void setUp() throws ParseException {
        /**
         * TODO read file vacation and prepare list (can also store in db)
          */
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
                long status = (Long) vacation.get("status");
                int statusInt = (int) status;
                VacationDto vacationDto = new VacationDto(vacationId, vacationTitle, startDate, endDate, statusInt, userId);
                vacationDtoList.add(vacationDto);
            }
        }
    }

    @Test
    public void simpleTest() {
        assertEquals(4, vacationDtoList.size());
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
