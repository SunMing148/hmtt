package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.wemedia.service.WmMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class WmMaterialController {

//    @Autowired
//    private WmMaterialService wmMaterialService;

    private final WmMaterialService wmMaterialService;

    //上传图片
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    //查所有图片素材或条件查询
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto){
        return wmMaterialService.findList(dto);
    }

    //删除图片
    @GetMapping("/del_picture/{id}")
    public ResponseResult delPicture(@PathVariable Integer   id){
        if(id==null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        WmMaterial wmMaterial = wmMaterialService.getById(id);
        if(wmMaterial==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        boolean b = wmMaterialService.removeById(id);
        if(b){
            return ResponseResult.okResult(wmMaterial);
        }else{
            return ResponseResult.errorResult(501,"文件删除失败");
        }
    }

    //收藏
    @GetMapping("/collect/{id}")
    public ResponseResult collect(@PathVariable Integer id){
        if(id==null)
            return ResponseResult.errorResult(0,"传输的收藏id为空");
        WmMaterial wmMaterial = wmMaterialService.getById(id);
//        System.out.println(wmMaterial);
        wmMaterial.setIsCollection((short) 1);
        wmMaterialService.updateById(wmMaterial);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    //取消收藏
    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelCollect(@PathVariable Integer id){
        if(id==null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        WmMaterial wmMaterial = wmMaterialService.getById(id);
//        System.out.println(wmMaterial);
        wmMaterial.setIsCollection((short) 0);
        wmMaterialService.updateById(wmMaterial);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}