package com.clinic.mapper;

import com.clinic.bean.*;

import java.util.List;

public interface OrderMapper {
    List<PatientOrder> selectByUid(String uid);
    List<PatientOrder> selectByDocid(String uid);
    int del(String onum);
    List<PatientOrder> selectAll(PatientOrder order);
    int updateApprove(String onum);
    int updateReject(String onum);
    int updateCancel(String onum);
    int updateConsult(String onum);
    int updateComplish(String onum);
    int addDrugOrder(DrugOrder order);
    List<DrugOrder> selectDrugOrderAll(DrugOrder order);
    int updatePrescript(String onum);

    List<DrugOrder> selectDrugOrderByUid(String uid);
    int addSurgery(SurgeryOrder order);
    int addAppoint(PatientOrder order);

    List<SurgeryOrder> selectSurByUid(String uid);

    List<SurgeryOrder> selectSurByDocid(String uid);

    int beginSurgery(String id);
    int endSurgery(String id);
    int deleteDrugOrder(String onum);
    int deleteSurgeryOrder(String id);
    List<SurgeryOrder> selectSurgeryAll(SurgeryOrder order);

    List<Drug> selectDrugAll(Drug drug);

    int updateDrug(Drug drug);

    int addDrug(Drug drug);

    int deleteDrug(String id);
}
