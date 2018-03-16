package com.framework.module.record.web;

import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.service.OperationRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "操作记录管理")
@RestController
@RequestMapping("/api/operationRecord")
public class OperationRecordController extends AbstractCrudController<OperationRecord> {
    private final OperationRecordService operationRecordService;

    @Override
    protected CrudService<OperationRecord> getService() {
        return operationRecordService;
    }

    @Autowired
    public OperationRecordController(OperationRecordService operationRecordService) {
        this.operationRecordService = operationRecordService;
    }
}
