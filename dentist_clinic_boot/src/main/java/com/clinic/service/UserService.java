package com.clinic.service;

import com.clinic.bean.Dentistry;
import com.clinic.bean.Page;
import com.clinic.bean.Room;
import com.clinic.bean.User;

public interface UserService {
    User queryUser(User user);
    User selectByUserName(String username);

    int add(User user);

    Page select(Page page, User user);
    Page selectDoc(Page page, User user);
    Page selectDocPrm(Page page, User user);
    int update(User user);

    int delete(Integer id);

    Page selectDentistry(Page page, Dentistry dentistry);

    Page selectRoom(Page page, Room room);

    Integer checkDiagnose(Integer id);

    Page selectSurRoom(Page page, Room room);

    Integer checkRoomStatus(String onum);

    Integer checkIsOrder(Integer id);

}
