package com.heima.apis.article.fallback;

import com.heima.apis.article.IArticleClient;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

/**
 * feign失败配置
 * @author itheima
 */
@Component
//hmall中使用的是fallbackfactory，会更好
//实现服务降级，此项目是在wemedia的nacos配置中心里添加feign:hystrix:enabled: true，开启服务降级
//TODO hmll中实现服务降级是用sentinel，包装简历时包装进去
public class IArticleClientFallback implements IArticleClient {
    @Override
    public ResponseResult saveArticle(ArticleDto dto)  {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }
}