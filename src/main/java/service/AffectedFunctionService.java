package service;

import model.AffectedItem;
import model.BugImpact;

import java.util.ArrayList;
import java.util.List;

public class AffectedFunctionService {

    List<AffectedItem> affectedItemList = new ArrayList<>();

    public List<AffectedItem> getAffectedItemList() {
        return affectedItemList;
    }

    public void setAffectedItemList(List<AffectedItem> affectedItemList) {
        this.affectedItemList = affectedItemList;
    }



}
