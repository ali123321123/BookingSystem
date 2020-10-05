package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.EmployeeMapper;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeListDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImp(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public EmployeeListDTO getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream().map(employee -> {
            EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
            employeeDTO.setEmployeeUrl(getEmployeeUrl(employee.getId()));
            return employeeDTO;
        }).collect(Collectors.toList());
        return new EmployeeListDTO(employeeDTOList);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::employeeToEmployeeDTO).map(employeeDTO -> {
            employeeDTO.setEmployeeUrl(getEmployeeUrl(id));
            return employeeDTO;
        }).orElseThrow(RuntimeException::new);//TODO make exception handler
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        return saveAndReturnDTO(employeeMapper.employeeDTOtoEmployee(employeeDTO));

    }

    @Override
    public EmployeeDTO saveEmployeeByDTO(Long id, EmployeeDTO employeeDTO) {
        Employee employeeToSave = employeeMapper.employeeDTOtoEmployee((employeeDTO));
        employeeToSave.setId(id);

        return saveAndReturnDTO(employeeToSave);


    }

   /* @Override
    public EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id).map(employee -> {
            if(employeeDTO.get)
        })

    }

    */

    @Override
    public void deleteEmployeeDTO(Long id) {
        employeeRepository.deleteById(id);

    }

    private String getEmployeeUrl(Long id) {
        return EmployeeController.BASE_URL + "/" + id;
    }

    private EmployeeDTO saveAndReturnDTO(Employee employee) {
        Employee savedEmloyee = employeeRepository.save(employee);
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(savedEmloyee);
        employeeDTO.setEmployeeUrl(getEmployeeUrl(savedEmloyee.getId()));
        return employeeDTO;

    }
}
