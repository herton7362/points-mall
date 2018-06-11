package com.framework.module.record.service;

import com.framework.module.record.web.OperationRecordResult;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.domain.OperationRecordRepository;
import com.framework.module.record.service.OperationRecordService;
import com.kratos.common.PageResult;
import com.kratos.module.auth.domain.OauthClientDetails;
import com.kratos.module.auth.service.OauthClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.StringUIClientPropertyKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OperationRecordServiceImpl extends AbstractCrudService<OperationRecord> implements OperationRecordService {
    private final OperationRecordRepository operationRecordRepository;
    private final OauthClientDetailsService oauthClientDetailsService;
    @Override
    protected PageRepository<OperationRecord> getRepository() {
        return operationRecordRepository;
    }

    @Override
    public PageResult<OperationRecordResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        PageResult<OperationRecord> page = findAll(pageRequest, param);
        PageResult<OperationRecordResult> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(translateResults(page.getContent()));
        return pageResult;
    }

    @Override
    public List<OperationRecordResult> findAllTranslated(Map<String, String[]> param) throws Exception {
        return null;
    }

    private List<OperationRecordResult> translateResults(List<OperationRecord> operationRecords) throws Exception {
        List<OperationRecordResult> operationRecordResults = new ArrayList<>();
        for (OperationRecord operationRecord : operationRecords) {
            operationRecordResults.add(this.translateResult(operationRecord));
        }
        return operationRecordResults;
    }

    private OperationRecordResult translateResult(OperationRecord operationRecord) throws Exception {
        OperationRecordResult operationRecordResult = new OperationRecordResult();
        BeanUtils.copyProperties(operationRecord, operationRecordResult);
        if(StringUtils.isNotBlank(operationRecord.getClientId())) {
            operationRecordResult.setClient(oauthClientDetailsService.findOneByClientId(operationRecord.getClientId()));
        }
        return operationRecordResult;
    }

    @Autowired
    public OperationRecordServiceImpl(
            OperationRecordRepository operationRecordRepository,
            OauthClientDetailsService oauthClientDetailsService
    ) {
        this.operationRecordRepository = operationRecordRepository;
        this.oauthClientDetailsService = oauthClientDetailsService;
    }
}
