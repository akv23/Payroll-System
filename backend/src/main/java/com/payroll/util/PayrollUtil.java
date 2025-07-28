package com.payroll.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.payroll.model.Frequency;
import com.payroll.model.PayComponent;
import com.payroll.model.SalaryComponentBreakdown;

@Component
public class PayrollUtil {

    public double calculateTax(double grossSalary) {
        if (grossSalary <= 25000) return grossSalary * 0.05;
        if (grossSalary <= 50000) return grossSalary * 0.10;
        if (grossSalary <= 100000) return grossSalary * 0.20;
        return grossSalary * 0.30;
    }

    public List<SalaryComponentBreakdown> calculateBreakdown(List<PayComponent> components) {
        List<SalaryComponentBreakdown> breakdownList = new ArrayList<>();

        for (PayComponent component : components) {
            double monthlyAmount = getMonthlyAmount(component);
            if (monthlyAmount > 0) {
                breakdownList.add(new SalaryComponentBreakdown(
                    component.getName(), monthlyAmount, component.getFrequency().toString()
                ));
            }
        }
        return breakdownList;
    }

    public double getMonthlyAmount(PayComponent component) {
        Set<Frequency> frequencies = component.getFrequency();
        double amount = 0;

        if (frequencies.contains(Frequency.MONTHLY)) amount = component.getAmount();
        else if (frequencies.contains(Frequency.ANNUALLY)) amount = component.getAmount() / 12;
        else if (frequencies.contains(Frequency.BIWEEKLY)) amount = component.getAmount() * 2;
        else if (frequencies.contains(Frequency.WEEKLY)) amount = component.getAmount() * 4;
        else if (frequencies.contains(Frequency.DAILY)) amount = component.getAmount() * 30;
        else if (frequencies.contains(Frequency.HOURLY)) amount = component.getAmount() * 8 * 22;
        else if (frequencies.contains(Frequency.ONE_TIME) && !component.isUsed()) {
            amount = component.getAmount();
            component.setUsed(true);
        }

        return amount;
    }

    public double calculateGrossSalary(List<SalaryComponentBreakdown> breakdownList) {
        return breakdownList.stream().mapToDouble(SalaryComponentBreakdown::getAmount).sum();
    }
}
