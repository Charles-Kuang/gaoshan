package com.ruoyi.journal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.journal.domain.GsOperationRecord;

import javax.servlet.http.HttpServletRequest;

public interface OperationRecordService extends IService<GsOperationRecord> {
    void addOperationRecord(GsOperationRecord operationRecord, HttpServletRequest request);
}
