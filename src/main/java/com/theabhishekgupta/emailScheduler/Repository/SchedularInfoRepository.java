package com.theabhishekgupta.emailScheduler.Repository;

import com.theabhishekgupta.emailScheduler.modle.ScheduledInfo;
import org.springframework.data.repository.CrudRepository;

public interface SchedularInfoRepository extends  CrudRepository<ScheduledInfo, Long>{
	
	public ScheduledInfo findByJobName(String jobName);
}
