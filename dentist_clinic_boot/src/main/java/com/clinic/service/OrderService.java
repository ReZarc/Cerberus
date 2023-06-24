package com.clinic.service;

import com.clinic.bean.*;

public interface OrderService {
    Page selectByUid(Page page, String uid);
    Page selectByDocid(Page page, String uid);
    int del(String onum);

    Page selectAll(Page page, PatientOrder order);
    int updateApprove(String onum);
    int updateReject(String onum);
    int updateCancel(String onum);
    int updateConsult(String onum);

    int updateComplish(String onum);

    int addDrugOrder(DrugOrder order);

    Page selectDrugOrderAll(Page page, DrugOrder order);

    int updatePrescript(String onum);

    Page selectDrugOrderByUid(Page page, String uid);

    int addSurgery(SurgeryOrder order);

    int addAppoint(PatientOrder order);

    Page selectSurByUid(Page page, String uid);

    Page selectSurByDocid(Page page, String uid);

    int beginSurgery(String id);

    int endSurgery(String id);

    int deleteDrugOrder(String onum);

    Page selectSurgeryAll(Page page, SurgeryOrder order);

    int deleteSurgeryOrder(String onum);

    Page selectDrugAll(Page page, Drug drug);

    int updateDrug(Drug drug);

    int addDrug(Drug drug);

    int deleteDrug(String id);
}
