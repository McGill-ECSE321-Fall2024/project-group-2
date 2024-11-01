package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class OwnerListDto {
    private List<OwnerDto> owner;

    public OwnerListDto(List<OwnerDto> owner) {
        this.owner = owner;
    }

    public List<OwnerDto> getOwner() {
        return owner;
    }

    public void setOwnerList(List <OwnerDto> owner) {
        this.owner = owner;
    }
}