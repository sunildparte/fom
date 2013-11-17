package com.histomon;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.histomon.api.SiteFilter;
import com.histomon.dao.SiteDAO;
import com.histomon.domain.SiteDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:appcontext.xml"})
public class SiteDAOTest {

	@Autowired
	SiteDAO dao;
	
	@Test
	public void getSites() {
		List<SiteDO> sites = dao.getSites( null );
		assertNotNull ( sites );
		assertEquals ( 3, sites.size());
	}
	
	@Test
	public void getSitesByType() {
		SiteFilter  sf = new SiteFilter();
		sf.setType(1);
		List<SiteDO> sites = dao.getSites( sf );
		assertNotNull ( sites );
		assertEquals ( 2, sites.size());
	}
	
	@Test
	public void getSitesByPage() {
		SiteFilter  sf = new SiteFilter();
		sf.setStartIndex(3);
		sf.setNumOfEntries(5);
		List<SiteDO> sites = dao.getSites( sf );
		assertNotNull ( sites );
		assertEquals ( 0, sites.size());
	}
}
