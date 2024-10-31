package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.OwnerService;


@RestController
public class OwnerRestController {

    @Autowired
    private OwnerService service;


    @GetMapping(value = { "/owners/{email}", "/owners/{email}/" })
    public OwnerDto getOwner(@PathVariable("email") String email) {
        Owner owner = service.getOwner(email);
        OwnerDto ownerBody = new OwnerDto(owner);
        return ownerBody;
    }


    @PostMapping(value = { "/owners/create", "/owners/create/" })
    public OwnerDto createOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = service.createOwner(
                ownerDto.getUserID(),
                ownerDto.getName(),
                ownerDto.getEmail(),
                ownerDto.getPassword());
        OwnerDto ownerBody = new OwnerDto(owner);
        return ownerBody;
    }


    @PutMapping(value = { "/owners/update/{newPassword}", "/owners/update/{newPassword}/" })
    public OwnerDto updateOwner(@RequestBody OwnerDto ownerDto,  @PathVariable("newPassword") String newPassword) {
        Owner owner = service.updateOwnerPassword(ownerDto.getEmail(), ownerDto.getPassword(), newPassword);
        OwnerDto ownerBody = new OwnerDto(owner);
        return ownerBody;
    }


    @PostMapping(value = { "/owners/delete", "/owners/delete/" })
    public Boolean deleteOwner(@RequestBody OwnerDto ownerDto) {
        Boolean deleted = service.deleteOwner(ownerDto.getEmail());
        return deleted;
    }
}