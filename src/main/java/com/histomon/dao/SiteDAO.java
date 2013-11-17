package com.histomon.dao;

import java.util.List;

import com.histomon.api.SiteFilter;
import com.histomon.domain.SiteDO;

public interface SiteDAO {
	List<SiteDO> getSites ( SiteFilter filter );
	SiteDO getSite ( Long id );
	void createSites ( List<SiteDO> sites );
	void updateSite ( SiteDO site );
	SiteDO getSiteByNicename( String nicename );
}
