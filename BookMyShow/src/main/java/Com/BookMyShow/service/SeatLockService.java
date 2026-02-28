package Com.BookMyShow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final StringRedisTemplate redisTemplate;

    public boolean lockSeat(String key) {
        Boolean locked = redisTemplate.opsForValue()
                .setIfAbsent(key, "LOCKED", Duration.ofMinutes(5));
        return Boolean.TRUE.equals(locked);
    }

    public void releaseSeat(String key) {
        redisTemplate.delete(key);
    }
}