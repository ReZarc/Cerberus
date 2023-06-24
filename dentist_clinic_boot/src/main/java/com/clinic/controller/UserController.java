package com.clinic.controller;

import com.clinic.bean.*;
import com.clinic.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController       // @Controller 实例化 + @ResponseBody : 设置响应数据为JSON格式
@RequestMapping("/user")
@CrossOrigin  // 解决跨域问题
public class UserController {
    @Resource
    private UserService service;

    @GetMapping("/selectAll")
    public ResultVo select(Page page, User user) {
        return ResultVo.success().setData(service.select(page, user));
    }
    @GetMapping("/selectDoc")
    public ResultVo selectDoc(Page page, User user) {
        return ResultVo.success().setData(service.selectDoc(page, user));
    }
    @GetMapping("/selectDocPrm")
    public ResultVo selectDocPrm(Page page, User user) {
        return ResultVo.success().setData(service.selectDocPrm(page, user));
    }
    @GetMapping("/selectRoom")
    public ResultVo selectRoom(Page page, Room room) {
        return ResultVo.success().setData(service.selectRoom(page, room));
    }
    @GetMapping("/selectSurRoom")
    public ResultVo selectSurRoom(Page page, Room room) {
        return ResultVo.success().setData(service.selectSurRoom(page, room));
    }
    @GetMapping("/selectDentistry")
    public ResultVo selectDentistry(Page page, Dentistry dentistry) {
        return ResultVo.success().setData(service.selectDentistry(page, dentistry));
    }
    @GetMapping("/login")
    public ResultVo login(User user) {
        User vo =  service.queryUser(user);
        if(vo == null){
            return  ResultVo.error("账号不存在！");
        }
        if(!vo.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))){
            return  ResultVo.error("密码错误！");
        }
        vo.password = "";
        return ResultVo.success().setData(vo);
    }
    @PostMapping("/add")
    public ResultVo add(@RequestBody User user) {
        int i = service.add(user);
        if(i > 0) return ResultVo.success();
        return ResultVo.error();
    }
    @GetMapping("/selectByUserName")
    public ResultVo selectByUserName(String username) {
        User user = service.selectByUserName(username);
        if(user != null){
            return ResultVo.error("用户名已存在！");
        }
        return ResultVo.success("用户名可用！");
    }
    @PutMapping
    public ResultVo update(@RequestBody User user) {
        User vo = service.queryUser(user);
        user.password = vo.password;
        if(service.update(user) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/changeProfile")
    public ResultVo changeProfile(@RequestBody User user) {
        User vo =  service.queryUser(user);
        if(!vo.getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))){
            return  ResultVo.error("密码错误！");
        }
        user.password = DigestUtils.md5DigestAsHex(user.password.getBytes());
        if(user.password1 != null && !user.password1.equals(""))
            user.password = DigestUtils.md5DigestAsHex(user.password1.getBytes());
        if(service.update(user) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @DeleteMapping
    public ResultVo delete(Integer id){
//        System.out.println(id);
        if(service.delete(id) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @GetMapping("/checkDiagnose")
    public ResultVo checkDiagnose(Integer id) {
        System.out.println(id);
        System.out.println(service.checkDiagnose(id));
        if(service.checkDiagnose(id) != null && service.checkDiagnose(id) > 0)
            return ResultVo.success();
        return ResultVo.error();
    }
    @GetMapping("/checkRoomStatus")
    public ResultVo checkRoomStatus(String onum) {
        if(service.checkRoomStatus(onum) != null && service.checkRoomStatus(onum) > 0)
            return ResultVo.success();
        return ResultVo.error();
    }
    @GetMapping("/checkIsOrder")
    public ResultVo checkIsOrder(Integer id) {
        if(service.checkIsOrder(id) == null || service.checkIsOrder(id) == 0)
            return ResultVo.success();
        return ResultVo.error();
    }
}
