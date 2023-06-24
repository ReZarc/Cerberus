package com.clinic.mapper;
import com.clinic.bean.Dentistry;
import com.clinic.bean.Room;
import com.clinic.bean.User;

import java.util.List;

public interface UserMapper {
    User queryUser(User user);
    User selectByUserName(String username);
    int add(User user);

    List<User> selectAll(User user);
    List<User> selectDoc(User user);

    List<User> selectDocPrm(User user);

    List<Dentistry> selectDentistry(Dentistry dentistry);

    List<Room> selectRoom(Room room);
    int update(User user);

    int delete(Integer id);

    Integer checkDiagnose(Integer id);

    List<Room> selectSurRoom(Room room);

    Integer checkRoomStatus(String onum);

    Integer checkIsOrder(Integer id);

}
