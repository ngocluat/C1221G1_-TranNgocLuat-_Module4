package com.furama.controller;

import com.furama.dto.ContractDetailDto;
import com.furama.model.ContractDetail;
import com.furama.model.CountDetail;
import com.furama.service.IAttachSrevice;
import com.furama.service.IContractDetailService;
import com.furama.service.IContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/detail")
@SessionAttributes("detail")
public class ContractDetailController {


    @Autowired
    private IAttachSrevice iAttachSrevice;


    @Autowired
    private IContractService iContractService;

    @ModelAttribute("detail")
    public CountDetail getCounterDetail() {
        return new CountDetail();
    }

    @Autowired
    private IContractDetailService iContractDetailService;

    @GetMapping("/contract-detail")
    public String goHomeContractDetail(Model model,
                                       Pageable pageable,
                                       @ModelAttribute("detail") CountDetail countDetail,
                                       HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("detail", "dfghooyu");
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        countDetail.getCountDeatil();
        model.addAttribute("contractDetail", iContractDetailService.lisContractDetails(pageable));
        return "contract/contractDetail";
    }

    @GetMapping("/add-new-contract-detail")
    public String goCreateContractDetail(Model model, Pageable pageable) {
        model.addAttribute("attach", iAttachSrevice.listAttachServices());
        model.addAttribute("contract", iContractService.listContract());
        model.addAttribute("contractDetailDto", new ContractDetailDto());
        return "contract/createContractDetail";
    }


    @PostMapping("/create-contract-detail")
    public String createContractDetail(@ModelAttribute ContractDetailDto contractDetailDto) {
        ContractDetail contractDetail = new ContractDetail();
        BeanUtils.copyProperties(contractDetailDto, contractDetail);
        iContractDetailService.saveContractDetail(contractDetail);
        return "redirect:/detail/contract-detail";
    }
}
