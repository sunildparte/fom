package com.histomon.dao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.histomon.api.SiteFilter;
import com.histomon.domain.SiteDO;

@Repository
public class SiteDAOImpl implements SiteDAO {

	private SessionFactory sessionFactory;
	
	@Autowired
	public SiteDAOImpl ( SessionFactory sessionFactory ) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<SiteDO> getSites(SiteFilter filter) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria( SiteDO.class );
		
		if ( filter != null ) {
			if ( filter.getStartIndex() != null ) {
				criteria.setFirstResult( filter.getStartIndex() );
			}
			
			if ( filter.getNumOfEntries() != null ) {
				criteria.setMaxResults( filter.getNumOfEntries() );
			}
			
			if ( filter.getType() != null ) {
				criteria.add(Restrictions.eq("type", filter.getType()));
			}
		}
		
		criteria.addOrder(Order.desc("rank"));
		
		List<SiteDO> sites = criteria.list();

		if ( CollectionUtils.isNotEmpty(sites)) {
			return sites;
		}
		return Collections.<SiteDO>emptyList();
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void createSites(List<SiteDO> sites) {
		
		if ( CollectionUtils.isEmpty(sites)) return;
		
		Session session = getCurrentSession();
		if ( CollectionUtils.isEmpty(sites)) return;
		
		for ( SiteDO site : sites ) {
			session.saveOrUpdate(site);
		}
	}

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public SiteDO getSite(Long id) {
		Session session = getCurrentSession();
		return (SiteDO)session.get(SiteDO.class, id);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void updateSite(SiteDO site) {
		Session session = getCurrentSession();
		session.update(site);
	}

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public SiteDO getSiteByNicename (String nicename) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria( SiteDO.class );
		criteria.add( Restrictions.eq("nicename", nicename));
		return (SiteDO)criteria.uniqueResult();
  }
	
}
