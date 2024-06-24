package com.project1.learning.services;

import java.util.List;
import java.util.stream.Collectors;

import com.project1.learning.dto.EmployeeDTO;
import com.project1.learning.entities.EmployeesEntity;
import com.project1.learning.repositories.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    final EmployeeRepo employeeRepo;
    final ModelMapper modelMapper;
    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id){
        EmployeesEntity employeesEntity = employeeRepo.getById(id);
        return modelMapper.map(employeesEntity , EmployeeDTO.class);
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO){
        EmployeesEntity employeesEntity = modelMapper.map(employeeDTO,EmployeesEntity.class);
        return modelMapper.map(employeeRepo.save(employeesEntity),EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepo
                .findAll()
                .stream()
                .map(employeesEntity -> modelMapper.map(employeesEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public boolean deleteEmployeeById(Long employeeID) {
        boolean isPresent = employeeRepo.existsById(employeeID);
        if(!isPresent) return false ;
        else {
            employeeRepo.deleteById(employeeID);
            return true;
            }

    }
}
