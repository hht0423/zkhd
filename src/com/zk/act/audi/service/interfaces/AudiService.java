package com.zk.act.audi.service.interfaces;

import java.util.HashMap;

import com.zk.act.audi.dto.ActBean;
import com.zk.common.baseclass.IBaseService;

public interface AudiService extends IBaseService{
    public HashMap<String,Object>  actRegister(ActBean actBean);
}
