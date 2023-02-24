package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.repository.UserRepository;
import uz.uzkassa.smartposrestaurant.service.CacheService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@RestController
@RequestMapping(ApiConstants.adminCacheRootApi)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class AdminCacheResource {

    CacheService cacheService;
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<String>> getList() {
        return ResponseEntity.ok(cacheService.getList());
    }

    @DeleteMapping("/{cacheName}")
    public ResponseEntity<Void> delete(@PathVariable String cacheName) {
        cacheService.delete(cacheName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUserCache(@PathVariable String userId) {
        cacheService.deleteUserCache(userRepository.findById(userId).orElse(null));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/evict/{cacheName}/{cacheKey}")
    public ResponseEntity<Void> evict(@PathVariable String cacheName, String cacheKey) {
        cacheService.evictCache(cacheName, cacheKey);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear/{cacheName}")
    public ResponseEntity<Void> evict(@PathVariable String cacheName) {
        cacheService.delete(cacheName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        cacheService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
