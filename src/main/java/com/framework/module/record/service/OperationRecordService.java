package com.framework.module.record.service;

import com.framework.module.record.web.OperationRecordResult;
import com.kratos.common.CrudService;
import com.framework.module.record.domain.OperationRecord;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface OperationRecordService extends CrudService<OperationRecord> {
    PageResult<OperationRecordResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception;

    List<OperationRecordResult> findAllTranslated(Map<String, String[]> param) throws Exception;
}
