package com.framework.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by He on 2017/4/19.
 */
@Controller
public class PageController {

    /**
     * 后台模板
     */
    @RequestMapping(value = "/admin/**",method = RequestMethod.GET)
    public ModelAndView adminTemplate(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(
                request.getRequestURI()
                .replaceFirst(request.getContextPath(), "")
                .replaceFirst("/", ""
                ), map);
    }

    /**
     * 首页
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index() throws Exception {
        return new ModelAndView("wechat/index");
    }

    /**
     * 微信前台模板
     */
    @RequestMapping(value = "/wechat/**",method = RequestMethod.GET)
    public ModelAndView portalTemplate(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(
                request.getRequestURI()
                        .replaceFirst(request.getContextPath(), "")
                        .replaceFirst("/", ""
                        ), map);
    }

    /**
     * 获取首页内容
     */
    @RequestMapping(value = "/admin/home/data",method = RequestMethod.GET)
    public ResponseEntity<Map> homeData() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Long freeMemory = runtime.freeMemory();
        Long totalMemory = runtime.totalMemory();
        Long maxMemory = runtime.maxMemory();
        Long usedMemory = totalMemory - freeMemory;
        Double maxD = maxMemory.doubleValue();
        Double usedD = usedMemory.doubleValue();
        Integer usedPercent = ((Double)(usedD / maxD * 100)).intValue();
        Map<String, Object> map = new HashMap<>();
        map.put("usedMemory", usedMemory/1024/1024);
        map.put("maxMemory", maxMemory/1024/1024);
        map.put("usedPercent", usedPercent);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
