package com.ahasan.file.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahasan.file.common.messages.BaseResponse;
import com.ahasan.file.dto.EmployeeDTO;
import com.ahasan.file.entity.EmployeeEntity;
import com.ahasan.file.repo.EmployeeRepo;


@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public List<EmployeeDTO> findEmpList() {
		return employeeRepo.findAll().stream().map(this::copyEmployeEntityToDto).collect(Collectors.toList());
	}

	public EmployeeDTO findByEmpId(Long empId) {
		EmployeeEntity employeeEntity = employeeRepo.findByEmployeeId(empId);
		return copyEmployeEntityToDto(employeeEntity);
	}

	public BaseResponse createOrUpdateEmployee(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity = copyEmployeDtoToEntity(employeeDTO);
		employeeRepo.save(employeeEntity);
		return new BaseResponse("Data Insert sucessfully", HttpStatus.CREATED.value());
	}

	public void deleteEmployee(Long empId) {
		employeeRepo.deleteById(empId);
	}

	private EmployeeDTO copyEmployeEntityToDto(EmployeeEntity employeeEntity) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employeeEntity, employeeDTO);
		return employeeDTO;
	}

	private EmployeeEntity copyEmployeDtoToEntity(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		return employeeEntity;
	}

}
