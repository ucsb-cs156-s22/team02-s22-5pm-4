package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.HelpRequest;
import edu.ucsb.cs156.example.repositories.HelpRequestRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(description = "HelpRequest")
@RequestMapping("/api/helprequest")
@RestController
@Slf4j
public class HelpRequestController extends ApiController {

    @Autowired
    HelpRequestRepository helpRequestRepository;

    @ApiOperation(value = "List all help requests")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<HelpRequest> all() {
        Iterable<HelpRequest> requests = helpRequestRepository.findAll();
        return requests;
    }

    @ApiOperation(value = "Create a new help request")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/post")
    public HelpRequest postHelpRequest(
            @ApiParam("Email of user making request") @RequestParam String requesterEmail,
            @ApiParam("ID of team") @RequestParam String teamId,
            @ApiParam("In-person table or Zoom breakout room (table or breakoutroom)") @RequestParam String tableOrBreakoutRoom,
            @ApiParam("Time (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime requestTime,
            @ApiParam("Explanation of issue") @RequestParam String explanation,
            @ApiParam("Whether issue has been resolved(true/false)") @RequestParam boolean solved)

            throws JsonProcessingException {

        // For an explanation of @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // See: https://www.baeldung.com/spring-date-parameters

        log.info("requestTime={}", requestTime);

        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setRequesterEmail(requesterEmail);
        helpRequest.setTeamId(teamId);
        helpRequest.setTableOrBreakoutRoom(tableOrBreakoutRoom);
        helpRequest.setRequestTime(requestTime);
        helpRequest.setExplanation(explanation);
        helpRequest.setSolved(solved);

        HelpRequest savedHelpRequest = helpRequestRepository.save(helpRequest);

        return savedHelpRequest;
    }


}
