package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBOrganization;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBOrganizationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(description = "UCSBOrganizaiton")
@RequestMapping("/api/ucsborganization")
@RestController
@Slf4j
public class UCSBOrganizationController extends ApiController {
    @Autowired
    UCSBOrganizationRepository ucsbOrganizationRepository;

    // GET
    @ApiOperation(value = "List all UCSB organizations")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBOrganization> allOrganizations() {
        Iterable<UCSBOrganization> organizations = ucsbOrganizationRepository.findAll();
        return organizations;
    }

    // POST
    @ApiOperation(value = "Create a new UCSB organization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBOrganization postOrganizations(
            @ApiParam("orgCode (ex. SKY)") @RequestParam String orgCode,
            @ApiParam("orgTranslationShort (ex.SKYDIVING CLUB)") @RequestParam String orgTranslationShort,
            @ApiParam("orgTranslation (ex.SKYDIVING CLUB AT UCSB)") @RequestParam String orgTranslation,
            @ApiParam("inactive (True if club is inactive)") @RequestParam boolean inactive) {

        UCSBOrganization organizations = new UCSBOrganization();
        organizations.setOrgCode(orgCode);
        organizations.setOrgTranslationShort(orgTranslationShort);
        organizations.setOrgTranslation(orgTranslation);
        organizations.setInactive(inactive);

        UCSBOrganization savedOrganizations = ucsbOrganizationRepository.save(organizations);

        return savedOrganizations;
    }
}
