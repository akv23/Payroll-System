package com.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payroll.dto.SalarySlipDTO;
import com.payroll.mapper.SalarySlipMapper;
import com.payroll.model.SalarySlip;
import com.payroll.service.SalarySlipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salary-slip")
@RequiredArgsConstructor
public class SalarySlipController {

    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping("/{empId}")
    public ResponseEntity<SalarySlipDTO> getSalarySlipsByEmpId(@PathVariable String empId) {
        return ResponseEntity.ok(salarySlipService.getSalarySlipsByEmpId(empId));
    }

    @GetMapping("/{empId}/pdf")
    public ResponseEntity<byte[]> downloadSalarySlipPdf(@PathVariable String empId) {
        SalarySlipDTO slipDto = salarySlipService.getSalarySlipsByEmpId(empId);
        SalarySlip slip = SalarySlipMapper.INSTANCE.toEntity(slipDto);
        byte[] pdf = salarySlipService.generateSalarySlipPdf(slip);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("SalarySlip_" + slip.getEmpId() + "_" + slip.getMonth() + "_" + slip.getYear() + ".pdf")
                .build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

   
    // Method to get salary slips by employee ID and month/year
    @GetMapping("/{empId}/month")
    public ResponseEntity<List<SalarySlipDTO>> getSalarySlipsByMonthYear(
            @PathVariable String empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(salarySlipService.getSalarySlipsByEmpIdMonthYear(empId, month, year));
    }
}

