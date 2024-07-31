package com.dnd.dndtravel.map.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dnd.dndtravel.map.service.MapService;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(MapController.class)
class MapControllerTest {

	@MockBean
	private MapService mapService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void 전체_지역정보를_조회한다() throws Exception {
		// when & then
		mockMvc.perform(get("/map/all"))
			.andExpect(status().isOk());
	}

}