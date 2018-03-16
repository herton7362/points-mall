package com.framework.module.record.service;

import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.domain.OperationRecordRepository;
import com.framework.module.record.service.OperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OperationRecordServiceImpl extends AbstractCrudService<OperationRecord> implements OperationRecordService {
    private final OperationRecordRepository operationRecordRepository;
    @Override
    protected PageRepository<OperationRecord> getRepository() {
        return operationRecordRepository;
    }

    @Autowired
    public OperationRecordServiceImpl(OperationRecordRepository operationRecordRepository) {
        this.operationRecordRepository = operationRecordRepository;
    }
}
