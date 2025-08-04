package com.payroll.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.payroll.dto.SalarySlipDTO;
import com.payroll.mapper.SalarySlipMapper;
import com.payroll.model.PaymentInfo;
import com.payroll.model.SalaryComponentBreakdown;
import com.payroll.model.SalarySlip;
import com.payroll.repository.SalarySlipRepository;

@Service
public class SalarySlipService {

    @Autowired
    private SalarySlipRepository salarySlipRepository;

    @Autowired
    private SalarySlipMapper salarySlipMapper;

    // Method to create a new salary slip
    public SalarySlip createSalarySlip(String empId, int month, int year, double netSalary,
                                       PaymentInfo paymentInfo, List<SalaryComponentBreakdown> breakdownList) {

        SalarySlip slip = new SalarySlip();
        slip.setId(UUID.randomUUID().toString());
        slip.setEmpId(empId);
        slip.setMonth(month);
        slip.setYear(year);
        slip.setNetSalary(netSalary);
        slip.setGeneratedDate(LocalDate.now());
        slip.setPaymentInfo(paymentInfo);
        slip.setBreakdown(breakdownList);

        return salarySlipRepository.save(slip);
    }

    // Method to get salary slips by employee ID
    public SalarySlipDTO getSalarySlipsByEmpId(String empId) {
        List<SalarySlip> slips = salarySlipRepository.findByEmpId(empId);
        if (slips == null || slips.isEmpty()) {
            throw new RuntimeException("Salary slip not found: " + empId);
        }
        return salarySlipMapper.toDto(slips.get(0));
    }

    // Method to get salary slips by employee ID and month/year
    public List<SalarySlipDTO> getSalarySlipsByEmpIdMonthYear(String empId, int month, int year) {
        List<SalarySlip> slips = salarySlipRepository.findByEmpIdAndMonthAndYear(empId, month, year);
        return salarySlipMapper.toDtoList(slips);
    }
    // Method to Generate PDF for a salary slip
    public byte[] generateSalarySlipPdf(SalarySlip slip) {
        return salarySlipPdfUtil.generateSalarySlipPdf(slip);
    }
}
