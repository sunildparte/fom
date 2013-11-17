package com.histomon.api;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.histomon.dao.SiteDAO;
import com.histomon.domain.SiteDO;
import com.histomon.dto.SiteDTO;

@Component
public class SiteServiceImpl implements SiteService {

	@Autowired
	private SiteDAO dao;
	
	@Autowired
	private SiteMapper mapper;
	
	public List<SiteDTO> getSites(int startIndex, int numOfEntries) {
		SiteFilter filter = new SiteFilter();
			
		if ( startIndex >= 0 ) {
			filter.setStartIndex(startIndex);
		}
		
		if ( numOfEntries > 0 ) {
			filter.setNumOfEntries(numOfEntries);
		}
		
		List<SiteDO> sites = dao.getSites(filter);
		return mapper.mapToDTO(sites);
	}

	public List<SiteDTO> getSitesByType(String type, int startIndex, int numOfEntries) {
		
		SiteFilter filter = new SiteFilter();;
		if ( startIndex >= 0 ) {
			filter.setStartIndex(startIndex);
		}
		
		if ( numOfEntries > 0 ) {
			filter.setNumOfEntries(numOfEntries);
		}
		
		if ( StringUtils.isNotEmpty(type)) {
			filter.setType(SiteTypeEnum.getByName(type).getId());
		}
		
		List<SiteDO> sites = dao.getSites(filter);
		return mapper.mapToDTO(sites);
	}

	public SiteDTO getSite ( Long id ) {
		SiteDO site = dao.getSite(id);
		return mapper.mapToDTO(site);
	}

	public void updateSite ( SiteDTO site ) {
		if ( site == null || site.getId() == null || StringUtils.isEmpty(site.getName()) ) {
			throw new SiteException("Invalid Data");
		}
		SiteDO org = dao.getSite(site.getId());
		
		org.setName( site.getName() );
		if ( StringUtils.isNotBlank( site.getDescription() )) org.setDescription( site.getDescription() );
		if ( StringUtils.isNotBlank( site.getCity() )) org.setCity( site.getCity() );
		if ( StringUtils.isNotBlank( site.getDistrict() )) org.setDistrict( site.getDistrict() );
		if ( StringUtils.isNotBlank( site.getLocation() )) org.setLocation( site.getLocation() );
		if ( StringUtils.isNotBlank( site.getImageUrl() )) org.setImageUrl( site.getImageUrl() );
		if ( CollectionUtils.isNotEmpty( site.getGallery() )) org.setGallery( StringUtils.join(site.getGallery(), ",") );
		
		if ( MapUtils.isNotEmpty(site.getDetails())) {
			 Map<String, String> orgDetails = SiteUtil.convertJsonToMap(org.getDetails());
			 orgDetails.putAll(site.getDetails());
			 org.setDetails( SiteUtil.convertMapToJson(orgDetails));
		}
		
		if ( StringUtils.isNotBlank( site.getHike()) ) {
				HikeTypeEnum hike = HikeTypeEnum.getByName(site.getHike());
				if ( hike != null ) org.setHike(hike.getId());
		}
		
		dao.updateSite(org);
	}

	public SiteDTO getSiteByNicename(String nicename) {
		SiteDO site = dao.getSiteByNicename(nicename);
		return mapper.mapToDTO(site);
  }
}
