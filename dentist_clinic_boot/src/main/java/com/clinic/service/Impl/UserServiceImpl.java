package com.clinic.service.Impl;

import com.clinic.bean.Dentistry;
import com.clinic.bean.Page;
import com.clinic.bean.Room;
import com.clinic.bean.User;
import com.clinic.mapper.UserMapper;
import com.clinic.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper mapper;  //自动注入

    @Override
    public User queryUser(User user) {
        return mapper.queryUser(user);
    }

    @Override
    public User selectByUserName(String username) {
        return mapper.selectByUserName(username);
    }

    @Override
    public int add(User user) {
        return mapper.add(user);
    }

    @Override
    public Page select(Page page, User user) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<User> list = mapper.selectAll(user);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page selectDoc(Page page, User user) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<User> list = mapper.selectDoc(user);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    @Override
    public Page selectDocPrm(Page page, User user) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<User> list = mapper.selectDocPrm(user);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    @Override
    public int update(User user) {
        return mapper.update(user);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public Page selectDentistry(Page page, Dentistry dentistry) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Dentistry> list = mapper.selectDentistry(dentistry);
        PageInfo<Dentistry> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page selectRoom(Page page, Room room) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Room> list = mapper.selectRoom(room);
        PageInfo<Room> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public Integer checkDiagnose(Integer id) {
        return mapper.checkDiagnose(id);
    }

    @Override
    public Page selectSurRoom(Page page, Room room) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Room> list = mapper.selectSurRoom(room);
        PageInfo<Room> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public Integer checkRoomStatus(String onum) {
        return mapper.checkRoomStatus(onum);
    }

    @Override
    public Integer checkIsOrder(Integer id) {
        return mapper.checkIsOrder(id);
    }


}
