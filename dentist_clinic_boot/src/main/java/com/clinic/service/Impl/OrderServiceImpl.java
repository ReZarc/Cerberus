package com.clinic.service.Impl;

import com.clinic.bean.*;
import com.clinic.mapper.OrderMapper;
import com.clinic.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderMapper mapper;

    @Override
    public Page selectByUid(Page page, String uid) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PatientOrder> orders = mapper.selectByUid(uid);
        PageInfo<PatientOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    @Override
    public Page selectByDocid(Page page, String uid) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PatientOrder> orders = mapper.selectByDocid(uid);
        PageInfo<PatientOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    @Override
    public int del(String onum) {
        return mapper.del(onum);
    }
    @Override
    public Page selectAll(Page page, PatientOrder order) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PatientOrder> list = mapper.selectAll(order);
        PageInfo<PatientOrder> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    @Override
    public int updateApprove(String onum) {
        return mapper.updateApprove(onum);
    }
    @Override
    public int updateReject(String onum) {
        return mapper.updateReject(onum);
    }
    @Override
    public int updateCancel(String onum) {
        return mapper.updateCancel(onum);
    }
    @Override
    public int updateConsult(String onum) { return mapper.updateConsult(onum); }
    @Override
    public int updateComplish(String onum) { return mapper.updateComplish(onum); }
    @Override
    public int addDrugOrder(DrugOrder order) { return mapper.addDrugOrder(order); }
    @Override
    public int updatePrescript(String onum) { return mapper.updatePrescript(onum); }

    @Override
    public Page selectDrugOrderByUid(Page page, String uid) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<DrugOrder> orders = mapper.selectDrugOrderByUid(uid);
        PageInfo<DrugOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public int addSurgery(SurgeryOrder order) {
        return mapper.addSurgery(order);
    }

    @Override
    public int addAppoint(PatientOrder order) {
        return mapper.addAppoint(order);
    }

    @Override
    public Page selectSurByUid(Page page, String uid) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<SurgeryOrder> orders = mapper.selectSurByUid(uid);
        PageInfo<SurgeryOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page selectSurByDocid(Page page, String uid) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<SurgeryOrder> orders = mapper.selectSurByDocid(uid);
        PageInfo<SurgeryOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public int beginSurgery(String id) {
        return mapper.beginSurgery(id);
    }

    @Override
    public int endSurgery(String id) {
        return mapper.endSurgery(id);
    }

    @Override
    public int deleteDrugOrder(String onum) {
        return mapper.deleteDrugOrder(onum);
    }

    @Override
    public Page selectSurgeryAll(Page page, SurgeryOrder order) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<SurgeryOrder> orders = mapper.selectSurgeryAll(order);
        PageInfo<SurgeryOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public int deleteSurgeryOrder(String onum) {
        return mapper.deleteSurgeryOrder(onum);
    }

    @Override
    public Page selectDrugAll(Page page, Drug drug) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Drug> list = mapper.selectDrugAll(drug);
        PageInfo<Drug> pageInfo = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(pageInfo.getTotal());
        return page;    }

    @Override
    public int updateDrug(Drug drug) {
        return mapper.updateDrug(drug);
    }

    @Override
    public int addDrug(Drug drug) {
        return mapper.addDrug(drug);
    }

    @Override
    public int deleteDrug(String id) {
        return mapper.deleteDrug(id);
    }

    @Override
    public Page selectDrugOrderAll(Page page, DrugOrder order) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<DrugOrder> orders = mapper.selectDrugOrderAll(order);
        PageInfo<DrugOrder> pageInfo = new PageInfo<>(orders);
        page.setRows(orders);
        page.setTotal(pageInfo.getTotal());
        return page;
    }


}
