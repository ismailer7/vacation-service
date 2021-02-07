package org.vacation.transformers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.vacation.beans.UserDto;
import org.vacation.models.User;

import java.util.Arrays;
import java.util.List;

public class UserTransformerImplTest {

    private UserTranformerImpl userTransformer = null;
    private User user = null;
    private UserDto userDto = null;
    private List<User> userList = null;
    private List<UserDto> userDtoList = null;

    @Before
    public void setUp() {

        userTransformer = new UserTranformerImpl();

        user = new User(1l, "IR91995");
        userDto = new UserDto(2l, "ir12457");

        userList = Arrays.asList(
                user,
                new User(1024l, "KB145879")
        );

        userDtoList = Arrays.asList(
                userDto,
                new UserDto(12589l, "HB14523")
        );

    }

    @Test
    public void testToDto() {
        UserDto userDto = userTransformer.toDto(user);
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getUserId());
        assertEquals(user.getUsername(), userDto.getUsername());
    }

    @Test
    public void testToEntity() {
        User user = userTransformer.toEntity(userDto);
        assertNotNull(user);
        assertEquals(userDto.getUserId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
    }

    @Test
    public void testToDtoList() {
        List<UserDto> userDtoList = userTransformer.toDtoList(userList);
        assertNotNull(userDtoList);
        assertEquals(userList.size(), userDtoList.size());
    }

    @Test
    public void testToEntityList() {
        List<User> userList = userTransformer.toEntityList(userDtoList);
        assertNotNull(userList);
        assertEquals(userDtoList.size(), userList.size());
    }

}
