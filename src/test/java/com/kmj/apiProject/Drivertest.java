package com.kmj.apiProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.driver.dao.DriverDao;
import com.kmj.apiProject.driver.service.DriverService;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class Drivertest {

	@Mock
    private DriverDao driverDao;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    private DriverService driverService;

    @BeforeEach
    void setUp() {
        driverService = new DriverService(driverDao, redisTemplate);
    }

    @Test
    void testChangeStatus_success() {
        // given
        DriverDto dto = new DriverDto();
        dto.setDriverId(1);
        dto.setStatus(1);

        // status()가 int를 반환한다고 가정
        Mockito.when(driverDao.status(Mockito.any(DriverDto.class))).thenReturn(1);

        // when
        Map<Object, Object> result = driverService.status(dto);

        // then
        assertEquals("S0000", result.get("code"));
    }
}
