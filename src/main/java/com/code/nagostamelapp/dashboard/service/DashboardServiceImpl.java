package com.code.nagostamelapp.dashboard.service;

import com.code.nagostamelapp.dashboard.controller.ConverterToRupiah;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.BalanceAPIHandling;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    UserService userService;

    public Model wrapModel(Model model, HttpSession session) throws UnirestException {
        UserModel userModel = userService.getUserByUsername(userService.getUsernameFromSession(session));
        String gopayToken = userModel.getGopayToken();
        String bankBCIToken = userModel.getBCIToken();
        String ovoToken = userModel.getOvoToken();

        Float ovoBalance = BalanceAPIHandling.getInstance().handleGetBalance(ovoToken, "ovo", session);
        Float BCIBalance = BalanceAPIHandling.getInstance().handleGetBalance(bankBCIToken, "BCI", session);
        Float gopayBalance = BalanceAPIHandling.getInstance().handleGetBalance(gopayToken, "gopay", session);
        Float cashBalance = userModel.getCashBalance();
        Float totalBalance = ovoBalance + gopayBalance + BCIBalance + cashBalance;

        String ovo = ConverterToRupiah.convert(ovoBalance);
        String bci = ConverterToRupiah.convert(BCIBalance);
        String gopay = ConverterToRupiah.convert(gopayBalance);
        String cash = ConverterToRupiah.convert(cashBalance);
        String total = ConverterToRupiah.convert(totalBalance);

        model.addAttribute("gopay", gopay);
        model.addAttribute("bci", bci);
        model.addAttribute("ovo", ovo);
        model.addAttribute("total", total);
        model.addAttribute("gopay_balance", gopayBalance);
        model.addAttribute("bci_balance", BCIBalance);
        model.addAttribute("ovo_balance", ovoBalance);
        model.addAttribute("cash_balance", cashBalance);
        model.addAttribute("cash", cash);
        model.addAttribute("user", userModel);
        return model;
    }
}
