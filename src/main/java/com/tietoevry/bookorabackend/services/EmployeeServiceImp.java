package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.EmployeeMapper;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeListDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.model.ConfirmationToken;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.ConfirmationTokenRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public EmployeeServiceImp(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository, EmailSenderService emailSenderService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenRepository = confirmationTokenRepository;
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
        return employeeRepository
                .findById(id)
                .map(employeeMapper::employeeToEmployeeDTO)
                .map(employeeDTO -> {
                    employeeDTO.setEmployeeUrl(getEmployeeUrl(id));
                    return employeeDTO;
                })
                .orElseThrow(RuntimeException::new);//TODO make exception handler
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {

        if(existedByEmail(employeeDTO.getEmail())){
            throw new RuntimeException("Email existed!"); //TODO make exception handler
        }
        else{
            Employee employee = employeeMapper.employeeDTOtoEmployee(employeeDTO);
            Employee savedEmployee = employeeRepository.save(employee);

            ConfirmationToken confirmationToken = new ConfirmationToken(savedEmployee);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(employee.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("oslomet8@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            EmployeeDTO employeeDTOtoReturn = employeeMapper.employeeToEmployeeDTO(savedEmployee);
            employeeDTO.setEmployeeUrl(getEmployeeUrl(savedEmployee.getId()));

            return employeeDTOtoReturn;
        }

    }

    @Override
    public EmployeeDTO saveEmployeeByDTO(Long id, EmployeeDTO employeeDTO) {
        Employee employeeToSave = employeeMapper.employeeDTOtoEmployee((employeeDTO));
        employeeToSave.setId(id);

        return saveAndReturnDTO(employeeToSave);
    }

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

    private boolean existedByEmail(String email){
        Employee employee = employeeRepository.findByEmailIgnoreCase(email);
        if (employee != null) return true;
        return false;
    }
}
