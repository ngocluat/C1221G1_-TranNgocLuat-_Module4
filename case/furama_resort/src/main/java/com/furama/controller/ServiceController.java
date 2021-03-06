package com.furama.controller;

import com.furama.dto.ServiceDto;
import com.furama.model.Service;
import com.furama.service.IServiceService;
import com.furama.service.ITypeServiceService;
import com.furama.service.ipml.RentTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private IServiceService iServiceService;

    @Autowired
    private ITypeServiceService iTypeServiceService;

    @Autowired
    private RentTypeService rentTypeService;

    @GetMapping(value = "")
    public String goHomeService(Model model, Pageable pageable) {
        model.addAttribute("service", iServiceService.listService(pageable));
        return "service/home";
    }

    @GetMapping(value = "/create-facility")
    public String goCreateFacility(Model model, Pageable pageable) {
        model.addAttribute("service", new ServiceDto());
        model.addAttribute("rentType", rentTypeService.listRent());
        model.addAttribute("typeService", iTypeServiceService.listSeviceType());
        return "service/create";
    }

    @PostMapping(value = "/add-service")
    public String CreateFacility(@ModelAttribute ServiceDto serviceDto,
                                 Model model,
                                 Pageable pageable,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes
    ) {
        Service service = new Service();
        BeanUtils.copyProperties(serviceDto, service);
        iServiceService.save(service);
        redirectAttributes.addFlashAttribute("message", " add new success !! ");
        return "redirect:/home-service";
    }
}
