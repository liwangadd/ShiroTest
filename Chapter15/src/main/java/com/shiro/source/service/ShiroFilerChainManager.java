package com.shiro.source.service;

import com.shiro.source.entity.UrlFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwang on 15-10-8.
 */
@Service
public class ShiroFilerChainManager {

    @Autowired
    private DefaultFilterChainManager filterChainManager;

    private Map<String, NamedFilterList> defaultFilterChains;

    public void init() {
        defaultFilterChains = new HashMap<>(filterChainManager.getFilterChains());
    }

    public void initFilterChains(List<UrlFilter> urlFilters) {
        filterChainManager.getFilterChains().clear();

        if (defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }

        for (UrlFilter urlFilter : urlFilters) {
            String url = urlFilter.getUrl();

            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
            }

            if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());
            }
        }
    }

}
