package com.histomon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.histomon.api.SiteTypeEnum;
import com.histomon.dao.SiteDAO;
import com.histomon.domain.SiteDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:appcontext.xml"})
public class WikiDataParser {

	private static final String FILE_NAME = "wikidata.xml";

	@Autowired
	SiteDAO dao;
	
	@Test
	public void parse2() throws Exception {
		Document document = getWikiDocument();
		NodeList mediaWikiChildren = getMediaWikiChildren ( document );
		List<SiteDO> sites = getSites ( mediaWikiChildren );
		removeInvalidSites ( sites );
		dao.createSites(sites);
	}
	

	private void removeInvalidSites(List<SiteDO> sites) {
		if ( CollectionUtils.isEmpty(sites)) return;
		
		Iterator<SiteDO> iter = sites.iterator();
		
		while ( iter.hasNext() ) {
			SiteDO item = iter.next();
			
			if ( StringUtils.isBlank(item.getName() )) {
				System.out.println("!# Invalid - " + item );
			}
		}
	}


	private List<SiteDO> getSites(NodeList mediaWikiChildren) {
		if ( mediaWikiChildren == null || mediaWikiChildren.getLength() <= 0 ) return Collections.<SiteDO>emptyList();
		
		Node fort = null;
		int fortCount = 0;
		int pages = 0;
		List<SiteDO> sites = new ArrayList<SiteDO>();
		
		for ( int i = 1; i < mediaWikiChildren.getLength() ; i ++ ) {
			fort = mediaWikiChildren.item(i);
			if ( fort.getNodeName().equals("page" )) {
				pages++;
				SiteDO site = getSite ( fort );
				if ( site == null ) continue;
				System.out.println("============");
				System.out.println(site);
				System.out.println("============");
				sites.add(site);
				fortCount++;
			}
		}
		
		System.out.println("Number of page tags : " + pages);
		System.out.println("Number of Forts (valid page) : " + fortCount);
		return sites;
		
	}

	private SiteDO getSite(Node fort) {
		NodeList nodeList = fort.getChildNodes();
		SiteDO site = new SiteDO();
		site.setType(SiteTypeEnum.FORT.getId());
		if ( nodeList == null || nodeList.getLength() == 0 ) return null;
		
		for ( int j = 0; j < nodeList.getLength() ; j++ ) {
			Node item = nodeList.item(j);
			if ( item == null ) continue;
			if ( item.getNodeName().equals("title")) {
				String title = item.getChildNodes().item(0).getNodeValue();
				if ( StringUtils.isBlank(title)) return null;
				if ( title.startsWith("Template:") ||  title.startsWith("Category:")) return null;
				site.setName( title );
			} else if (item.getNodeName().equals("id")) {
				site.setExternalid( item.getChildNodes().item(0).getNodeValue());
				site.setExternalSource("wikipedia");
			} else if ( item.getNodeName().equals("revision")) {
				parseHeadRevision ( site, item );
			}
		}
		return site;
	}

	private void parseHeadRevision(SiteDO site, Node node ) {
		NodeList nodeList = node.getChildNodes();
		if ( nodeList == null || nodeList.getLength() == 0 ) return;
		
		for ( int i = 0; i< nodeList.getLength(); i++ ) {
			Node item = nodeList.item(i);
			
			if ( item == null ) continue;
			if ( item.getNodeName().equals("text")) {
				Map<String, String> infoBoxMap = parseInfoBox ( item.getChildNodes().item(0).getNodeValue() );
				setLocation ( site, infoBoxMap.get("location"));
				
				if (StringUtils.isNotBlank( infoBoxMap.get("latitude")) && StringUtils.isNotBlank( infoBoxMap.get("longitude"))) {
					site.setLocation(   stz(infoBoxMap.get("latitude")) + "," + stz(infoBoxMap.get("longitude")));
				}
				setImage ( site, infoBoxMap.get("image"));
				setDescription ( site, item.getChildNodes().item(0).getNodeValue() );
			}
		}
	}

	private void setDescription(SiteDO site, String nodeValue) {
		if ( StringUtils.isBlank(nodeValue)) return;
		
		nodeValue = heavyStz ( nodeValue );
		
		nodeValue = nodeValue.trim();
		Pattern dp = Pattern.compile("'''(.*?)==", Pattern.DOTALL);
		Matcher dm = dp.matcher(nodeValue);
		String desc = null;
		if ( dm.find() ) {
			desc = dm.group(1);
			if ( StringUtils.isBlank(desc)) return;
			desc.replaceAll("'''", "");
			stz(desc);
			if ( desc.length() > 3999 ) {
				desc = desc.substring(0, 3999);
			}
			desc = stz ( desc);
			desc = desc.replaceAll("[\\r\\n\\t]", " ");
			desc = desc.replaceAll("'''", "");
			desc = desc.replaceAll("\\(\\{\\{lang-en.*?\\}\\}\\)", "");
			desc = desc.replaceAll("\\(\\{\\{audio.*?\\}\\}\\)", "");
			desc = desc.replaceAll("\\(\\{\\{lang-mr.*?\\}\\}\\)", "");
			desc = desc.replaceAll("\\{\\{cn.*?\\}\\}", "");
			desc = desc.replaceAll("(?s)\\{\\{\\s?Citation.*?\\}\\}", "");
			desc = desc.replaceAll("(?i)\\{\\{when.*?\\}\\}", "");
			desc = desc.replaceAll("(?i)\\{\\{fact.*?\\}\\}", "");
			Matcher m = Pattern.compile("(?i)(\\{\\{convert\\|)(.*?)\\|(.*?)\\|(.*?\\}\\})").matcher(desc);
			
			while ( m.find() ) {
				String asis = m.group(0);
				String to = m.group(2) + " " + m.group(3);
				desc = desc.replace(asis, to);
			}
			site.setDescription(desc);
		} 
	}


	private String heavyStz(String nodeValue) {
		String INFOBOX_CONST_STR = "{{Infobox";
	    int startPos = nodeValue.indexOf(INFOBOX_CONST_STR);
	    
	    if ( startPos >= 0 ) {
		    int bracketCount = 2;
		    int endPos = startPos + INFOBOX_CONST_STR.length();
		    for(; endPos < nodeValue.length(); endPos++) {
		      switch(nodeValue.charAt(endPos)) {
		        case '}':
		          bracketCount--;
		          break;
		        case '{':
		          bracketCount++;
		          break;
		        default:
		      }
		      if(bracketCount == 0) break;
		    }
		    String infoBoxText = nodeValue.substring(startPos, endPos+1);
		    nodeValue = nodeValue.substring(endPos + 1);
	    }
	    
	    nodeValue = stripCite(nodeValue); // strip clumsy {{cite}} tags
	    // strip any html formatting
	    nodeValue = nodeValue.replaceAll("&gt;", ">");
	    nodeValue = nodeValue.replaceAll("&lt;", "<");
	    nodeValue = nodeValue.replaceAll("<ref.*?>.*?</ref>", " ");
	    nodeValue = nodeValue.replaceAll("</?.*?>", " ");
	    return nodeValue;
	}


	private void setImage(SiteDO site, String file) {
		if ( StringUtils.isBlank(file)) return;
		file = file.replaceAll("\\|.*?\\]\\]", "");
		file = file.replaceAll("\\[\\[File:|\\[\\[file:|\\[\\[Image:|\\[\\[image:", "");
		file = file.replaceAll("\\s.*?", "_");
		site.setImageUrl(file);
	}

	private String stz ( String str ) {
		if (StringUtils.isBlank(str)) return str;
		str = str.replaceAll("\\[\\[", "");
		str = str.replaceAll("\\]\\]", "");
		return str.trim();
	}
	private void setLocation(SiteDO site, String location) {
		if ( StringUtils.isBlank(location)) location = "";
		location = location.replaceAll("\\{\\{.*?\\}\\}", "");
		location = location.replaceAll("\\[\\[", "");
		location = location.replaceAll("\\]\\]", "");
		
		StringTokenizer stk = new StringTokenizer(location, ",");
		
		while (stk.hasMoreTokens()) {
			String token = stk.nextToken();
			if ( StringUtils.isBlank(token)) continue;
			token = token.trim();
			if ( !token.equalsIgnoreCase("Maharashtra") && !token.equalsIgnoreCase("india")) {
				token = token.replaceAll("District|district", "");
				//token.replaceAll("district", "");
				site.setCity(token.trim());
			}
		}
		
		site.setState("Maharashtra");
		site.setCountry("IN");
	}

	private Map<String, String> parseInfoBox( String nodeValue ) {
		Map<String, String> result = new HashMap<String, String>();
		
		String INFOBOX_CONST_STR = "{{Infobox";
	    int startPos = nodeValue.indexOf(INFOBOX_CONST_STR);
	    if(startPos < 0) return result;
	    
	    int bracketCount = 2;
	    int endPos = startPos + INFOBOX_CONST_STR.length();
	    for(; endPos < nodeValue.length(); endPos++) {
	      switch(nodeValue.charAt(endPos)) {
	        case '}':
	          bracketCount--;
	          break;
	        case '{':
	          bracketCount++;
	          break;
	        default:
	      }
	      if(bracketCount == 0) break;
	    }
	    
	    String infoBoxText = nodeValue.substring(startPos, endPos+1);
	    infoBoxText = stripCite(infoBoxText); // strip clumsy {{cite}} tags
	    // strip any html formatting
	    infoBoxText = infoBoxText.replaceAll("&gt;", ">");
	    infoBoxText = infoBoxText.replaceAll("&lt;", "<");
	    infoBoxText = infoBoxText.replaceAll("<ref.*?>.*?</ref>", " ");
	    infoBoxText = infoBoxText.replaceAll("</?.*?>", " ");
	    
	    //System.out.println( infoBoxText );
	    
	    infoBoxText = infoBoxText.replaceAll("\\{\\{Infobox\\s*?\\S*?\\s*?\\|", " ");
	    infoBoxText = infoBoxText.substring(0, infoBoxText.length() - 2 );
	    infoBoxText = infoBoxText.replaceAll("\\{\\{.*?\\}\\}", " ");
	    //System.out.println( infoBoxText );
	    
	    StringTokenizer stk = new StringTokenizer(infoBoxText, "|");
	    StringTokenizer keyvalue = null;
	    while ( stk.hasMoreTokens()) {
	    	String token = stk.nextToken();
	    	if ( StringUtils.isBlank(token)) continue;
	    	keyvalue = new StringTokenizer(token, "=" );
	    	if ( keyvalue.countTokens() != 2 ) continue;
	    	String key = keyvalue.nextToken();
	    	String value = keyvalue.nextToken();
	    	
	    	if ( StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
	    		result.put(key.trim(), value.trim());
	    	}
	    }
	    return result;
	  }
	
	private String stripCite(String text) {
	    String CITE_CONST_STR = "{{cite";
	    int startPos = text.indexOf(CITE_CONST_STR);
	    if(startPos < 0) return text;
	    int bracketCount = 2;
	    int endPos = startPos + CITE_CONST_STR.length();
	    for(; endPos < text.length(); endPos++) {
	      switch(text.charAt(endPos)) {
	        case '}':
	          bracketCount--;
	          break;
	        case '{':
	          bracketCount++;
	          break;
	        default:
	      }
	      if(bracketCount == 0) break;
	    }
	    text = text.substring(0, startPos-1) + text.substring(endPos);
	    return stripCite(text);   
	  }
	
	private NodeList getMediaWikiChildren(Document root) {
		Node mediaWiki = root.getChildNodes().item(0);
		NodeList mediaWikiChildren = mediaWiki.getChildNodes();
		//System.out.println("Number of MediaWiki children : " + (mediaWikiChildren.getLength()) );
		return mediaWikiChildren;
	}

	private Document getWikiDocument() throws Exception {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setValidating(false);
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		return builder.parse(this.getClass().getClassLoader().getResourceAsStream(FILE_NAME));
	}
	
	public static void main(String[] args) throws Exception {
/*		InputStreamReader str = new InputStreamReader(WikiDataParser.class.getClassLoader().getResourceAsStream("sample.txt"));
		BufferedReader reader = new BufferedReader(str);
		StringBuilder sb = new StringBuilder();
		
		String line = reader.readLine();
		
		while ( line != null ) {
			sb.append(line);
			line = reader.readLine();
		}
		
		new WikiDataParser().setDescription(new SiteDO(), sb.toString());*/
		
		String str = "western pinnacle and Tungi, {{Convert|4366|ft|m|abbr=on}} high, the";
		
		Matcher m = Pattern.compile("(?i)(\\{\\{convert\\|)(.*?)\\|(.*?)\\|(.*?\\}\\})").matcher(str);
		
		if ( m.find() ) {
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
			System.out.println(m.group(4));
		}
		System.out.println(str);

				
	}
}
