package com.xbang.bootdemo.dao.mapper;

import com.xbang.bootdemo.cache.RedisCache;
import com.xbang.bootdemo.dao.entity.TProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xbang
 * @since 2019-09-10
 */
@Repository
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface TProductMapper extends BaseMapper<TProduct> {

    @Select("select * from t_product where id =#{productId} for update")
    TProduct selectByIdForLock(Integer productId);

}
