package com.clinic.controller;

import com.clinic.bean.*;
import com.clinic.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.clinic.utils.OrderNumUtils.getOrderNum;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService service;
    @GetMapping("/selectAll")
    public ResultVo selectAll(Page page, PatientOrder order) {
        return ResultVo.success().setData(service.selectAll(page, order));
    }
    @GetMapping("/selectByUid")
    public ResultVo selectByUid(Page page,String uid){
        return ResultVo.success().setData(service.selectByUid(page,uid));
    }
    @GetMapping("/selectByDocid")
    public ResultVo selectByDocid(Page page,String uid){
        return ResultVo.success().setData(service.selectByDocid(page,uid));
    }
    @GetMapping("/selectDrugOrderAll")
    public ResultVo selectDrugOrderAll(Page page,DrugOrder order){
        return ResultVo.success().setData(service.selectDrugOrderAll(page,order));
    }
    @GetMapping("/selectDrugAll")
    public ResultVo selectDrugAll(Page page,Drug drug){
        return ResultVo.success().setData(service.selectDrugAll(page,drug));
    }

    @GetMapping("/selectDrugOrderByUid")
    public ResultVo selectDrugOrder(Page page,String uid){
        return ResultVo.success().setData(service.selectDrugOrderByUid(page,uid));
    }
    @GetMapping("/selectSurByUid")
    public ResultVo selectSurByUid(Page page,String uid){
        return ResultVo.success().setData(service.selectSurByUid(page,uid));
    }
    @GetMapping("/selectSurByDocid")
    public ResultVo selectSurByDocid(Page page,String uid){
        return ResultVo.success().setData(service.selectSurByDocid(page,uid));
    }
    @GetMapping("/selectSurgeryAll")
    public ResultVo selectSurgeryAll(Page page,SurgeryOrder order){
        return ResultVo.success().setData(service.selectSurgeryAll(page,order));
    }
    @DeleteMapping
    public ResultVo del(String onum){
        return ResultVo.success().setData(service.del(onum));
    }
    @DeleteMapping("/deleteDrugOrder")
    public ResultVo deleteDrugOrder(String onum) {
        return ResultVo.success().setData(service.deleteDrugOrder(onum));
    }
    @DeleteMapping("/deleteSurgeryOrder")
    public ResultVo deleteSurgeryOrder(String id) {
        System.out.println(id);
        return ResultVo.success().setData(service.deleteSurgeryOrder(id));
    }
    @PutMapping("/approve")
    public ResultVo updateApprove(String onum) {
        if(service.updateApprove(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/beginSurgery")
    public ResultVo beginSurgery(String id) {
        if(service.beginSurgery(id) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/endSurgery")
    public ResultVo endSurgery(String id) {
        if(service.endSurgery(id) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/consult")
    public ResultVo updateConsult(String onum) {
        if(service.updateConsult(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/reject")
    public ResultVo updateReject(String onum) {
        if(service.updateReject(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/cancelOrder")
    public ResultVo updateCancel(String onum) {
        if(service.updateCancel(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/complish")
    public ResultVo updateComplish(String onum) {
        if(service.updateComplish(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PutMapping("/prescript")
    public ResultVo updatePrescript(String onum) {
        if(service.updatePrescript(onum) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PostMapping("/addDrugOrder")
    public ResultVo addDrugOrder(@RequestBody DrugOrder order) {
        int i = service.addDrugOrder(order);
        if(i > 0) return ResultVo.success();
        return ResultVo.error();
    }
    @PutMapping("/updateDrug")
    public ResultVo updateDrug(@RequestBody Drug drug) {
        if(service.updateDrug(drug) > 0){
            return ResultVo.success();
        }
        return  ResultVo.error();
    }
    @PostMapping("/addSurgery")
    public ResultVo addSurgery(@RequestBody SurgeryOrder order) {
        System.out.println(order.onum);
        int i = service.addSurgery(order);
        if(i > 0) return ResultVo.success();
        return ResultVo.error();
    }
    @PostMapping("/addAppoint")
    public ResultVo addAppoint(@RequestBody PatientOrder order) {
        order.onum = getOrderNum();
        System.out.println(order.onum);
        System.out.println(order.dentistryId);
        int i = service.addAppoint(order);
        if(i > 0) return ResultVo.success();
        return ResultVo.error();
    }
    @PostMapping("/addDrug")
    public ResultVo addDrug(@RequestBody Drug drug) {
        int i = service.addDrug(drug);
        if(i > 0) return ResultVo.success();
        return ResultVo.error();
    }
    @DeleteMapping("/deleteDrug")
    public ResultVo deleteDrug(String id) {
        return ResultVo.success().setData(service.deleteDrug(id));
    }

}
