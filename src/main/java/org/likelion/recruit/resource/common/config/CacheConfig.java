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
    public CaffeineCache executiveMembersSearchCache() {
        return new CaffeineCache(
                "executiveMembersSearch",
                Caffeine.newBuilder()
                        .maximumSize(4)
                        .expireAfterWrite(7, TimeUnit.DAYS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache projectsSearchCache() {
        return new CaffeineCache(
                "projectsSearch",
                Caffeine.newBuilder()
                        .maximumSize(15)
                        .expireAfterWrite(7, TimeUnit.DAYS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache applicationsSearchCache() {
        return new CaffeineCache(
                "applicationsSearch",
                Caffeine.newBuilder()
                        .maximumSize(1)
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache interviewTimeFindAllCache() {
        return new CaffeineCache(
                "interviewTimeFindAll",
                Caffeine.newBuilder()
                        .maximumSize(4)
                        .expireAfterWrite(7, TimeUnit.DAYS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache getQuestionCache() {
        return new CaffeineCache(
                "questions",
                Caffeine.newBuilder()
                        .maximumSize(3)
                        .expireAfterWrite(7, TimeUnit.DAYS)
                        .recordStats()
                        .build()
        );
    }

    @Bean
    public CaffeineCache IdByPublicIdCache() {
        return new CaffeineCache(
                "idByPublicId",
                Caffeine.newBuilder()
                        .maximumSize(100)
                        .expireAfterWrite(1, TimeUnit.DAYS)
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
