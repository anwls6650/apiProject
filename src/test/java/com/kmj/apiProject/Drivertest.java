package com.kmj.apiProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.driver.service.DriverService;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class Drivertest {

	@Mock
	private DriverService driverService;

	@Test
	void testChangeStatus_success() {
		// given
		DriverDto dto = new DriverDto();
		dto.setStatus(1);
		dto.setDriverId(1);

		Map<Object, Object> mockResult = new HashMap();
		mockResult.put("code", "0000");

		// when
		Mockito.when(driverService.status(dto)).thenReturn(mockResult);

		// then
		Map<Object, Object> result = driverService.status(dto);
		assertEquals("0000", result.get("code"));
	}
}
