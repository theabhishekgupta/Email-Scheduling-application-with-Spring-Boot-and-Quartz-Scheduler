package com.theabhishekgupta.emailScheduler.controller;

import javax.validation.Valid;

import com.theabhishekgupta.emailScheduler.modle.ScheduledInfo;
import com.theabhishekgupta.emailScheduler.payloadDTO.EmailRequest;
import com.theabhishekgupta.emailScheduler.payloadDTO.EmailRescheduleRequest;
import com.theabhishekgupta.emailScheduler.payloadDTO.EmailResponse;
import com.theabhishekgupta.emailScheduler.service.SchedularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class EmailSchedulerController {

	@Autowired
    SchedularService schedularService;

	@GetMapping("/schedule")
	public ResponseEntity<List<ScheduledInfo>> schedule() {

		List<ScheduledInfo> findAllJob = schedularService.findAllJob();
		return ResponseEntity.status(HttpStatus.OK).body(findAllJob);
	}

	@PostMapping("/scheduleEmail")
	public ResponseEntity<EmailResponse> scheduleEmail(@Valid @RequestBody EmailRequest emailRequest) {
		ZonedDateTime dateTime = ZonedDateTime.of(emailRequest.getDateTime(), emailRequest.getTimeZone());
		if (dateTime.isBefore(ZonedDateTime.now())) {
			EmailResponse EmailResponse = new EmailResponse(false, "dateTime must be after current time");
			return new ResponseEntity<>(EmailResponse, HttpStatus.BAD_REQUEST);
		} else

			return schedularService.scheduleEmailJob(emailRequest, dateTime);
	}

	@PutMapping("/RescheduleEmail")
	public ResponseEntity<EmailResponse> reScheduleEmail(
			@Valid @RequestBody EmailRescheduleRequest emailRescheduleRequest) {

		ZonedDateTime dateTime = ZonedDateTime.of(emailRescheduleRequest.getDateTime(),
				emailRescheduleRequest.getTimeZone());
		if (dateTime.isBefore(ZonedDateTime.now())) {
			EmailResponse EmailResponse = new EmailResponse(false, "dateTime must be after current time");
			return new ResponseEntity<>(EmailResponse, HttpStatus.BAD_REQUEST);
		} else

			return schedularService.reScheduleEmailJob(emailRescheduleRequest, dateTime);
	}

	@DeleteMapping("/schedule/{jobName}/{jobGroup}")
	public ResponseEntity<?> schedule(@PathVariable String jobName, @PathVariable String jobGroup) {
		return schedularService.deleteJob(jobName, jobGroup);
	}

}
