package com.framework.module.record.web;

import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.service.OperationRecordService;
import com.kratos.common.PageParam;
import com.kratos.common.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "操作记录管理")
@RestController
@RequestMapping("/api/operationRecord")
public class OperationRecordController extends AbstractCrudController<OperationRecord> {
    private final OperationRecordService operationRecordService;

    @Override
    protected CrudService<OperationRecord> getService() {
        return operationRecordService;
    }

    public ResponseEntity<?> searchPagedList(@ModelAttribute PageParam pageParam, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<OperationRecordResult> page = operationRecordService.findAllTranslated(pageParam.getPageRequest(), param);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        List<OperationRecordResult> list = operationRecordService.findAllTranslated(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Autowired
    public OperationRecordController(OperationRecordService operationRecordService) {
        this.operationRecordService = operationRecordService;
    }
}
