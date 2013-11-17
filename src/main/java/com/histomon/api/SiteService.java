package com.histomon.api;

import java.util.List;

import com.histomon.dto.SiteDTO;

public interface SiteService {
	List<SiteDTO> getSites ( int startIndex, int numOfEntries );
	SiteDTO getSite ( Long id );
	List<SiteDTO> getSitesByType ( String type, int startIndex, int numOfEntries );
	void updateSite ( SiteDTO site );
	SiteDTO getSiteByNicename ( String nicename );
}
