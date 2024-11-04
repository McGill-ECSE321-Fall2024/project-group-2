package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class EmployeeListDto {
    private List<EmployeeResponseDto> employees;

    public EmployeeListDto(List<EmployeeResponseDto> employees) {
        this.employees = employees;
    }

    public List<EmployeeResponseDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponseDto> employees) {
        this.employees = employees;
    }
}
