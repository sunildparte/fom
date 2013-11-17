package com.histomon.ui.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.histomon.api.SiteService;
import com.histomon.dto.SiteDTO;

@Controller
@RequestMapping("/histomonapi")
public class HomeController {
	
	@Autowired
	private SiteService service;
	
	@RequestMapping(value={"/sites"}, method= { RequestMethod.GET, RequestMethod.POST} )
	public @ResponseBody List<SiteDTO> listSites ( 
			@RequestParam(value="startindex", required=false) Integer startIndex, 
			@RequestParam(value="numofentries", required=false) Integer numOfEntries,
			@RequestParam(value="type", required=false) String type
			) {
		if ( startIndex == null || startIndex < 0 ) startIndex = 0;
		if ( numOfEntries == null || numOfEntries < 0 ) numOfEntries = 100;
		if ( StringUtils.isNotEmpty(type)) {
			return service.getSitesByType(type, startIndex, numOfEntries);
		} else {
			return service.getSites(startIndex, numOfEntries);
		}
	}
	
	@RequestMapping(value={"/site/{nicename}"}, method= { RequestMethod.GET, RequestMethod.POST} )
	public @ResponseBody SiteDTO listSites ( 
			@PathVariable(value="nicename" ) String nicename ) {
			return service.getSiteByNicename(nicename);
	}
	
	@RequestMapping(value={"/savesite"}, method= { RequestMethod.POST} )
	public @ResponseBody String saveSite ( @RequestBody SiteDTO site ) {
		service.updateSite(site);
		return "Success";
	}
}
