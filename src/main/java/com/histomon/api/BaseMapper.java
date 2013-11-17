package com.histomon.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseMapper<DTO, DO> {
	public abstract DTO mapToDTO ( DO dataObject);
	public abstract DO mapToDO ( DTO dto);
	
	public List<DO> mapToDO( Collection<DTO> dtoList) {
		List<DO> list = new ArrayList<DO>();
		for(DTO dto: dtoList){
			list.add(mapToDO(dto));
		}
		return list;
	}
	
	public List<DTO> mapToDTO( Collection<DO> doList) {
		List<DTO> list = new ArrayList<DTO>();
		
		for(DO _do: doList){
			list.add(mapToDTO(_do));
		}
		return list;
	}
}
