package com.shiro.source.dao;

import com.shiro.source.entity.UrlFilter;

import java.util.List;

/**
 * Created by liwang on 15-10-8.
 */
public interface UrlFilterDao {

    public UrlFilter createUrlFilter(UrlFilter urlFilter);
    public UrlFilter updateUrlFilter(UrlFilter urlFilter);
    public void deleteUrlFilter(Long urlFilterId);

    public UrlFilter findOne(Long urlFilterId);
    public List<UrlFilter> findAll();

}
