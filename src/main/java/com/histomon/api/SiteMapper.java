package com.histomon.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.histomon.domain.SiteDO;
import com.histomon.dto.SiteDTO;

@Component
public class SiteMapper extends BaseMapper<SiteDTO, SiteDO>{

	@Value("${sites.images.url.prefix}")
	private String imageUrlSuffix;
	
	@Override
	public SiteDTO mapToDTO(SiteDO dataObject) {
		SiteDTO site = new SiteDTO();
		site.setId( dataObject.getId());
		site.setName( dataObject.getName());
		site.setDescription( dataObject.getDescription());
		
		if ( dataObject.getType() != null ) {
			site.setType( SiteTypeEnum.getById( dataObject.getType()));
		}
		
		if ( StringUtils.isNotBlank(dataObject.getImageUrl())) {
			site.setImageUrl( imageUrlSuffix + dataObject.getImageUrl() );
		} else {
			site.setImageUrl( imageUrlSuffix + "generic.jpg" );
		}
		
		site.setAddress1( dataObject.getAddress1());
		site.setCity( dataObject.getCity());
		site.setState( dataObject.getState());
		site.setCountry( dataObject.getCountry());
		site.setZip( dataObject.getZip());
		site.setLocation( dataObject.getLocation());
		site.setExternalId(dataObject.getExternalid());
		site.setExternalSource(dataObject.getExternalSource());
		
		
		site.setDistrict(dataObject.getDistrict());
		site.setDetails( SiteUtil.convertJsonToMap (dataObject.getDetails()) );
		
		if ( dataObject.getHike() != null ) {
			site.setHike(HikeTypeEnum.getById(dataObject.getHike()).getName());
		}
		if ( StringUtils.isNotBlank(dataObject.getGallery())) {
			List<String> images = new ArrayList<String>();
			StringTokenizer str = new StringTokenizer(dataObject.getGallery(), ",");
			while ( str.hasMoreTokens() ) {
					String token = str.nextToken();
					if ( StringUtils.isNotBlank( token )) {
						images.add( imageUrlSuffix + token );
					}
			}
			site.setGallery(images);
		}
		site.setRank(dataObject.getRank());
		site.setNicename(dataObject.getNicename());
		return site;
	}

	@Override
	public SiteDO mapToDO(SiteDTO dto) {
		return null;
	}
}
