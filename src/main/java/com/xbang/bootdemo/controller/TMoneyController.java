package com.xbang.bootdemo.controller;


import com.xbang.bootdemo.dao.entity.TMoney;
import com.xbang.bootdemo.service.face.ITMoneyService;
import com.xbang.commons.exception.BaseException;
import com.xbang.commons.vo.result.BaseResult;
import com.xbang.commons.vo.result.Result;
import com.xbang.commons.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(BaseException.class)
    public Result exception(BaseException baseException){
        return BaseResult.getResult(ResultEnum.RESULT_EXCEPTION,baseException.getError_msg());
    }

}

