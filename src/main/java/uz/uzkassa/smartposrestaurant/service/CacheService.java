package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.auth.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 17:13
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class CacheService implements CacheConstants {
    CacheManager cacheManager;

    public void deleteAll() {
        cacheManager
            .getCacheNames()
            .forEach(cacheName -> {
                try {
                    Cache cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        cache.clear();
                    }
                } catch (Exception e) {
                    log.error("Delete cache {} error {}: ", cacheName, e.getMessage());
                }
            });
    }

    public List<String> getList() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }

    public void delete(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    public void evictCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    public void deleteUserCache(User user) {
        if (user == null) {
            return;
        }
        this.deleteUserCache(user.getLogin());

    }

    public void deleteUserCache(String login) {
        Cache cache = cacheManager.getCache(USERS_BY_LOGIN_CACHE);
        if (cache != null) {
            cache.evict(login);
        }
    }
}
