package com.payroll.util;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.payroll.dto.EmployeeDataResponseDTO;
import com.payroll.model.PaymentInfo;
import com.payroll.model.SalaryComponentBreakdown;
import com.payroll.model.SalarySlip;
import com.payroll.service.EmployeeDataService;
import com.payroll.util.PayrollUtil;

@Component
public class SalarySlipPdfUtil {

    @Autowired
    private EmployeeDataService employeeDataService;

    @Autowired
    private PayrollUtil payrollUtil;

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font COMPANY_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private static final Font SMALL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 9);
    private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8);
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Generates a PDF byte array for the given salary slip
     * 
     * @param slip The salary slip to generate PDF for
     * @return byte array containing the PDF data
     * @throws RuntimeException if PDF generation fails
     */
    public byte[] generateSalarySlipPdf(SalarySlip slip) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            // Add company header
            addCompanyHeader(document, slip);
            
            // Add employee and bank details in two columns
            addEmployeeAndBankDetails(document, slip);
            
            // Add salary breakdown and deductions in two columns
            addSalaryBreakdownAndDeductions(document, slip);
            
            // Add footer
            addFooter(document);
            
            document.close();
            return out.toByteArray();
            
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during PDF generation: " + e.getMessage(), e);
        }
    }

    private void addCompanyHeader(Document document, SalarySlip slip) throws DocumentException {
        try {
            // Fetch employee data to get company information
            EmployeeDataResponseDTO employeeData = employeeDataService.getEmployeeDataByEmpId(slip.getEmpId());
            
            // Company name centered
            String companyName = employeeData.getCompanyDTO() != null ? 
                employeeData.getCompanyDTO().getCompanyName() : "MED MANOR ORGANICS PVT LTD";
            Paragraph companyNamePara = new Paragraph(companyName, COMPANY_FONT);
            companyNamePara.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(companyNamePara);
            
            // Company address centered
            String companyAddress = formatCompanyAddress(employeeData.getCompanyDTO());
            Paragraph companyAddressPara = new Paragraph(companyAddress, NORMAL_FONT);
            companyAddressPara.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(companyAddressPara);
            
        } catch (Exception e) {
            // Fallback to default values if employee data not found
            Paragraph companyName = new Paragraph("MED MANOR ORGANICS PVT LTD", COMPANY_FONT);
            companyName.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(companyName);
            
            Paragraph companyAddress = new Paragraph("16-11-477/45, SRI KRISHNA NILAYAM DILSUKH NAGAR, HYDERABAD--500036", NORMAL_FONT);
            companyAddress.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(companyAddress);
        }
        
        // Salary slip title
        Paragraph salarySlipTitle = new Paragraph("Salary Slip for the month of " + getMonthAbbreviation(slip.getMonth()) + "-" + slip.getYear(), TITLE_FONT);
        salarySlipTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(salarySlipTitle);
        
        document.add(new Paragraph("\n"));
    }

    private void addEmployeeAndBankDetails(Document document, SalarySlip slip) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Employee details column
        PdfPTable employeeTable = new PdfPTable(1);
        employeeTable.addCell(createCell("Employee Details", HEADER_FONT, true));
        employeeTable.addCell(createCell("Emp Code: " + slip.getEmpId(), NORMAL_FONT, false));
        employeeTable.addCell(createCell("Name: " + getEmployeeName(slip), NORMAL_FONT, false));
        employeeTable.addCell(createCell("Designation: " + getEmployeeDesignation(slip), NORMAL_FONT, false));
        employeeTable.addCell(createCell("National ID: " + getNationalId(slip), NORMAL_FONT, false));
        employeeTable.addCell(createCell("Address: " + getEmployeeAddress(slip), NORMAL_FONT, false));
        
        // Bank details column
        PdfPTable bankTable = new PdfPTable(1);
        bankTable.addCell(createCell("Bank Details", HEADER_FONT, true));
        PaymentInfo paymentInfo = slip.getPaymentInfo();
        if (paymentInfo != null) {
            bankTable.addCell(createCell("Bank: " + paymentInfo.getBankName(), NORMAL_FONT, false));
            bankTable.addCell(createCell("Account: " + paymentInfo.getAccountNumber(), NORMAL_FONT, false));
            bankTable.addCell(createCell("IFSC: " + paymentInfo.getIfscCode(), NORMAL_FONT, false));
        }
        bankTable.addCell(createCell("Total Payout: Rs. " + formatCurrency(slip.getNetSalary()), HEADER_FONT, false));
        
        table.addCell(employeeTable);
        table.addCell(bankTable);
        document.add(table);
    }

    private void addSalaryBreakdownAndDeductions(Document document, SalarySlip slip) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Calculate total earnings
        double totalEarnings = 0;
        if (slip.getBreakdown() != null && !slip.getBreakdown().isEmpty()) {
            for (SalaryComponentBreakdown comp : slip.getBreakdown()) {
                totalEarnings += comp.getAmount();
            }
        }
        
        // Calculate tax using PayrollUtil
        double taxAmount = payrollUtil.calculateTax(totalEarnings);
        
        // Salary breakdown column
        PdfPTable breakdownTable = new PdfPTable(2);
        breakdownTable.addCell(createCell("Earnings", HEADER_FONT, true));
        breakdownTable.addCell(createCell("Amount", HEADER_FONT, true));
        
        if (slip.getBreakdown() != null && !slip.getBreakdown().isEmpty()) {
            for (SalaryComponentBreakdown comp : slip.getBreakdown()) {
                if (comp.getAmount() > 0) {
                    breakdownTable.addCell(createCell(comp.getName(), NORMAL_FONT, false));
                    breakdownTable.addCell(createCell("Rs. " + formatCurrency(comp.getAmount()), NORMAL_FONT, false));
                }
            }
        }
        
        // Add total earnings row
        breakdownTable.addCell(createCell("Total Earnings", HEADER_FONT, false));
        breakdownTable.addCell(createCell("Rs. " + formatCurrency(totalEarnings), HEADER_FONT, false));
        
        // Deductions column
        PdfPTable deductionsTable = new PdfPTable(2);
        deductionsTable.addCell(createCell("Deductions", HEADER_FONT, true));
        deductionsTable.addCell(createCell("Amount", HEADER_FONT, true));
        
        // Add tax deduction
        if (taxAmount > 0) {
            deductionsTable.addCell(createCell("Tax", NORMAL_FONT, false));
            deductionsTable.addCell(createCell("Rs. " + formatCurrency(taxAmount), NORMAL_FONT, false));
        }
        
        // Add total deductions row
        deductionsTable.addCell(createCell("Total Deductions", HEADER_FONT, false));
        deductionsTable.addCell(createCell("Rs. " + formatCurrency(taxAmount), HEADER_FONT, false));
        
        table.addCell(breakdownTable);
        table.addCell(deductionsTable);
        document.add(table);
        document.add(new Paragraph("\n"));
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("This is system generated Document signature not required.", FOOTER_FONT);
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(footer);
        
        Paragraph timestamp = new Paragraph("Generated on: " + LocalDateTime.now().format(TIME_FORMATTER), FOOTER_FONT);
        timestamp.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(timestamp);
    }

    private com.itextpdf.text.pdf.PdfPCell createCell(String text, Font font, boolean isHeader) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new Paragraph(text, font));
        cell.setBorder(com.itextpdf.text.Rectangle.BOX);
        cell.setPadding(5);
        if (isHeader) {
            cell.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
        }
        return cell;
    }

    private String getMonthAbbreviation(int month) {
        String[] monthAbbr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                             "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthAbbr[month - 1];
    }

    private String getEmployeeName(SalarySlip slip) {
        try {
            EmployeeDataResponseDTO employeeData = employeeDataService.getEmployeeDataByEmpId(slip.getEmpId());
            if (employeeData.getEmployeeDTO() != null) {
                String firstName = employeeData.getEmployeeDTO().getFirstName() != null ? 
                    employeeData.getEmployeeDTO().getFirstName() : "";
                String lastName = employeeData.getEmployeeDTO().getLastName() != null ? 
                    employeeData.getEmployeeDTO().getLastName() : "";
                return firstName + " " + lastName;
            }
        } catch (Exception e) {
            // Fallback to default
        }
        return "Employee Name";
    }

    private String getEmployeeDesignation(SalarySlip slip) {
        try {
            EmployeeDataResponseDTO employeeData = employeeDataService.getEmployeeDataByEmpId(slip.getEmpId());
            if (employeeData.getJobInfoDTO() != null && employeeData.getJobInfoDTO().getJobTitle() != null) {
                return employeeData.getJobInfoDTO().getJobTitle();
            }
        } catch (Exception e) {
            // Fallback to default
        }
        return "Designation";
    }

    private String getNationalId(SalarySlip slip) {
        try {
            EmployeeDataResponseDTO employeeData = employeeDataService.getEmployeeDataByEmpId(slip.getEmpId());
            if (employeeData.getEmployeeDTO() != null && employeeData.getEmployeeDTO().getNationalId() != null) {
                return employeeData.getEmployeeDTO().getNationalId();
            }
        } catch (Exception e) {
            // Fallback to default
        }
        return "National ID";
    }

    private String getEmployeeAddress(SalarySlip slip) {
        try {
            EmployeeDataResponseDTO employeeData = employeeDataService.getEmployeeDataByEmpId(slip.getEmpId());
            if (employeeData.getEmployeeDTO() != null && employeeData.getEmployeeDTO().getAddressDTO() != null) {
                return formatEmployeeAddress(employeeData.getEmployeeDTO().getAddressDTO());
            }
        } catch (Exception e) {
            // Fallback to default
        }
        return "Address not available";
    }



    private String formatCurrency(double amount) {
        return String.format("%,.2f", amount);
    }

    private String formatCompanyAddress(com.payroll.dto.CompanyDTO companyDTO) {
        if (companyDTO == null || companyDTO.getAddressDTO() == null) {
            return "16-11-477/45, SRI KRISHNA NILAYAM DILSUKH NAGAR, HYDERABAD--500036";
        }
        
        StringBuilder address = new StringBuilder();
        com.payroll.dto.AddressDTO addressDTO = companyDTO.getAddressDTO();
        
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().isEmpty()) {
            address.append(addressDTO.getStreet());
        }
        
        if (addressDTO.getCity() != null && !addressDTO.getCity().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(addressDTO.getCity());
        }
        
        if (addressDTO.getState() != null && !addressDTO.getState().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(addressDTO.getState());
        }
        
        if (addressDTO.getPostalCode() != null && !addressDTO.getPostalCode().isEmpty()) {
            if (address.length() > 0) address.append("--");
            address.append(addressDTO.getPostalCode());
        }
        
        return address.length() > 0 ? address.toString() : "16-11-477/45, SRI KRISHNA NILAYAM DILSUKH NAGAR, HYDERABAD--500036";
    }

    private String formatEmployeeAddress(com.payroll.dto.AddressDTO addressDTO) {
        if (addressDTO == null) {
            return "Address not available";
        }
        
        StringBuilder address = new StringBuilder();
        
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().isEmpty()) {
            address.append(addressDTO.getStreet());
        }
        
        if (addressDTO.getCity() != null && !addressDTO.getCity().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(addressDTO.getCity());
        }
        
        if (addressDTO.getState() != null && !addressDTO.getState().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(addressDTO.getState());
        }
        
        if (addressDTO.getPostalCode() != null && !addressDTO.getPostalCode().isEmpty()) {
            if (address.length() > 0) address.append(" ");
            address.append(addressDTO.getPostalCode());
        }
        
        return address.length() > 0 ? address.toString() : "Address not available";
    }
} 
