package org.likelion.recruit.resource.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCache executiveMembersCache() {
        return new CaffeineCache(
                "executiveMembers",
                Caffeine.newBuilder()
                        .maximumSize(4)
                        .expireAfterWrite(7, TimeUnit.DAYS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache hourlyCache() {
        return new CaffeineCache(
                "otherData",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caches) {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }
}
