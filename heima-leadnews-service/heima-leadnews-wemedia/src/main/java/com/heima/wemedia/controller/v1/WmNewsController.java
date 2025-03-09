package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class WmNewsController {

    private final WmNewsService wmNewsService;

    //查全部文章或分条件查询
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    //新增提交文章
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return  wmNewsService.submitNews(dto);
    }

    //查看详情
    @GetMapping("/one/{id}")
    public ResponseResult collect(@PathVariable Integer id){return wmNewsService.collect(id);}

    //文章删除
    @GetMapping("/del_news/{id}")
    public ResponseResult deleteNews(@PathVariable Integer id){return wmNewsService.deleteNews(id);}


    //文章上下架
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto){
        return wmNewsService.downOrUp(dto);
    }

}