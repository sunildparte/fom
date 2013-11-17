package com.histomon;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.histomon.api.SiteService;
import com.histomon.dto.SiteDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:appcontext.xml"})
public class SiteServiceTest {

	@Autowired
	SiteService service;
	
	@Test
	public void getSitesByPage() {
		List<SiteDTO> sites = service.getSites(-1, -1);
		assertEquals(3, sites.size());
		
		sites = service.getSites(2, 5);
		assertEquals(1, sites.size());
		
		sites = service.getSites(0, 5);
		assertEquals(3, sites.size());
	}
	
	@Test
	public void getSitesByType() {
		List<SiteDTO> sites = service.getSitesByType("fort", -1, -1);
		assertEquals(2, sites.size());
		
		sites = service.getSitesByType("fort", 0, 5);
		assertEquals(2, sites.size());
	}
}
