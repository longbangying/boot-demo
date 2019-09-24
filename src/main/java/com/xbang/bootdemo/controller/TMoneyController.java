package com.xbang.bootdemo.controller;


import com.xbang.bootdemo.dao.entity.TMoney;
import com.xbang.bootdemo.service.face.ITMoneyService;
import com.xbang.bootdemo.utils.DistributedLockUtils;
import com.xbang.bootdemo.utils.TOOL;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xbang
 * @since 2019-09-20
 */
@RestController
@RequestMapping("/tMoney")
public class TMoneyController {

    private final ITMoneyService itMoneyService;

    @Autowired
    public TMoneyController(ITMoneyService itMoneyService) {
        this.itMoneyService = itMoneyService;
    }

    @RequestMapping("increase")
    public Result increase(@RequestParam("id") Long id){
        itMoneyService.increase(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }
    @RequestMapping("decrease")
    public Result decrease(@RequestParam("id") Long id){
        itMoneyService.decrease(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }

    @RequestMapping("/increase/optimisticlock")
    public Result increaseOptimisticlock(@RequestParam("id") Long id){
        itMoneyService.increaseWithOptimisticLock(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }
    @RequestMapping("/decrease/optimisticlock")
    public Result decreaseOptimisticlock(@RequestParam("id") Long id){
        itMoneyService.decreaseWithOptimisticLock(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }


    @RequestMapping("statistical")
    public Result statistical(){
        Map<String,Long> resultMap = new HashMap<>();
        resultMap.put("success", TOOL.getSuccessCount());
        resultMap.put("fail",TOOL.getFailCount());
        resultMap.put("total",TOOL.getTotal());
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,resultMap);
    }

    @RequestMapping("clear")
    public Result clear(){
      Result result = statistical();
      TOOL.clear();
      return result;
    }

    @RequestMapping("/increaseWithDistributedLock")
    public Result increaseWithDistributedLock(@RequestParam("id") Long id , HttpServletRequest request){
        request.setAttribute("requestId",UUID.randomUUID().toString());
        itMoneyService.increaseWithDistributedLock(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }

    @RequestMapping("decreaseWithDistributedLock")
    public Result decreaseWithDistributedLock(@RequestParam("id") Long id){
        itMoneyService.decreaseWithDistributedLock(id);
        return Result.getResult(ResultEnum.RESULT_SUCCESS);
    }

    @Autowired
    DistributedLockUtils distributedLockUtils;
    @RequestMapping("getStatisticalInfo")
    public Result getStatisticalInfo(){
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,distributedLockUtils.getStatisticalInfo());
    }

    @RequestMapping("clearStatisticalInfo")
    public Result clearStatisticalInfo(){
        return BaseResult.getResult(ResultEnum.RESULT_SUCCESS,distributedLockUtils.clearStatisticalInfo());
    }


    @ExceptionHandler(BaseException.class)
    public Result exception(BaseException baseException){
        return BaseResult.getResult(ResultEnum.RESULT_EXCEPTION,baseException.getError_msg());
    }

}

