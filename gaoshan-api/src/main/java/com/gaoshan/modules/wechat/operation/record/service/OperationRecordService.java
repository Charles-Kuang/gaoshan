package com.gaoshan.modules.wechat.operation.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.modules.wechat.operation.record.domain.OperationRecord;

public interface OperationRecordService extends IService<OperationRecord> {
    void addOperationRecord(OperationRecord operationRecord);
}
